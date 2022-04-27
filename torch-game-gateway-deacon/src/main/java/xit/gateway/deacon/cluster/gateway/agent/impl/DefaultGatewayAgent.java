package xit.gateway.deacon.cluster.gateway.agent.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.api.cluster.gateway.agent.GatewayAgent;
import xit.gateway.constant.ResultCode;
import xit.gateway.pojo.Gateway;
import xit.gateway.pojo.ResultInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DefaultGatewayAgent implements GatewayAgent {
    private final WebClient webClient;
    private final String password;
    private final Logger logger = LoggerFactory.getLogger(DefaultGatewayAgent.class);

    public DefaultGatewayAgent(@Value("${torch.gateway.core.password}") String password){
        this.webClient = WebClient.create();
        this.password = password;
    }

    // 这一部分的注释详见DefaultHttpRequester类，都是类似的
    @Override
    public Mono<?> proxy(String serviceId, Gateway gateway, ServerWebExchange exchange) {
        Mono<Void> result;
        WebClient.RequestHeadersSpec<?> spec;
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        spec = webClient.method(request.getMethod())
                .uri(resolveServiceUri(gateway, request))
                .headers((headers) -> {
                    headers.putAll(request.getHeaders());
                    headers.remove("HOST"); // 由于是MultiMap，所以不能直接add覆盖，要先删除原值
                    headers.add("HOST", gateway.getHost());
                })
                .body(BodyInserters.fromDataBuffers(request.getBody()));

        transformCookiesToSpec(request, spec);

        // 调用、处理响应结果或异常
        result = spec.exchangeToMono(proxyResponse -> {
            prepareResponse(response, proxyResponse);
            return response.writeWith(proxyResponse.bodyToMono(DataBuffer.class));
        }).onErrorResume(ex -> Mono.defer(() -> {
            String errorMsg = "网关实例调用失败：" + ex.getLocalizedMessage();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMsg.getBytes())));
        }).then(Mono.empty()));

        return result;
    }

    private String resolveServiceUri(Gateway gateway, ServerHttpRequest request){
        String path = request.getPath().value();
        List<String> queryParamList =
                request.getQueryParams().entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + "=" + StringUtils.join(entry.getValue(), ","))
                        .collect(Collectors.toList());;
        String result = (gateway.getUseSSL() ? "https" : "http") +
                "://" +
                gateway.getHost() +
                ":" +
                gateway.getPort() +
                "/service" +
                StringUtils.substring(path, StringUtils.indexOf(path, "service") + 7) +
                (
                        queryParamList.size() == 0 ?
                                ""
                                :
                                "?" + StringUtils.join(queryParamList, "&")
                );

        // 删除最后一个“/”，避免core那边拼接完成后链接出现“xxx/?”的情况
        return result.charAt(result.length() - 1) == '/' ?
                result.substring(0, result.length() - 1)
                :
                result;
    }

    private void transformCookiesToSpec(ServerHttpRequest request, WebClient.RequestHeadersSpec<?> spec){
        for (Map.Entry<String, List<HttpCookie>> entry : request.getCookies().entrySet()){
            for (HttpCookie cookie : entry.getValue()){
                spec.cookie(cookie.getName(), cookie.getValue());
            }
        }
    }

    private void prepareResponse(ServerHttpResponse response, ClientResponse proxyResponse){
        response.getHeaders().putAll(proxyResponse.headers().asHttpHeaders());
        response.getCookies().putAll(proxyResponse.cookies());
        response.setStatusCode(proxyResponse.statusCode());
    }

    @Override
    public void disableRoute(Gateway gateway, String serviceId, String routeId) {
        String url = (gateway.getUseSSL() ? "https" : "http") +
                "://" +
                gateway.getHost() +
                ":" +
                gateway.getPort() +
                "/action/disable-route/" +
                serviceId + "/" + routeId + "/" +
                password;

        webClient.post()
                .uri(url)
                .exchangeToMono(response -> response.bodyToMono(ResultInfo.class)
                        .map(resultInfo -> {
                            if (!ResultCode.OK.getValue().equals(resultInfo.getCode())){
                                logger.warn("fuse failed, serviceId: {}, routeId: {}", serviceId, routeId);
                            }

                            return resultInfo;
                        })).subscribe();
    }
}
