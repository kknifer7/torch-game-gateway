package xit.gateway.core.request.requester.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.core.request.requester.HttpRequester;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.pojo.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Knifer
 * Description: HTTP REST 请求器实现。
 * Date: 2022/03/27
 */
public class DefaultHttpRequester implements HttpRequester {
    private final RouteGroup routeGroup;
    private final List<String> keys;                // 为routes所有路由的名称（服务名），用于从容器中定位Requester
    private final Map<String, List<Route>> routes;
    private final WebClient webClient;

    public DefaultHttpRequester(RouteGroup routeGroup) {
        Map<String, List<Route>> httpRoutes = routeGroup.getHttpRoutes();

        this.routeGroup = routeGroup;
        this.routes = this.routeGroup.getHttpRoutes();
        this.webClient = WebClient.create(routeGroup.getBaseUrl());
        this.keys = new ArrayList<>();
        if (!CollectionUtils.isEmpty(httpRoutes)){
            keys.addAll(httpRoutes.keySet());
        }
    }

    private Mono<Void> invoke(String serviceName, Route route, ServerWebExchange exchange) {
        Mono<Void> result;
        WebClient.RequestHeadersSpec<?> spec;
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        spec = webClient.method(request.getMethod())
                .uri(resolveServiceUri(serviceName, route, request))
                .headers((headers) -> {
                    headers.putAll(request.getHeaders());
                    headers.remove("HOST"); // 由于是MultiMap，所以不能直接add覆盖，要先删除原值
                    headers.add("HOST", route.getHost());
                })
                .body(BodyInserters.fromDataBuffers(request.getBody()));

        transformCookiesToSpec(request, spec);

        // 调用、处理响应结果或异常
        result = spec.exchangeToMono(proxyResponse -> {
            prepareResponse(response, proxyResponse);
            return response.writeWith(proxyResponse.bodyToMono(DataBuffer.class));
        }).onErrorResume(ex -> Mono.defer(() -> {
            String errorMsg = "服务调用失败：" + ex.getLocalizedMessage();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMsg.getBytes())));
        }).then(Mono.empty()));

        return result;
    }

    /**
     * 解析出访问目标服务的路径和url上的参数
     * @param serviceName 服务名称
     * @param request 请求
     * @return path
     */
    private String resolveServiceUri(String serviceName, Route route, ServerHttpRequest request){
        List<String> queryParamList =
                request.getQueryParams().entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + "=" + StringUtils.join(entry.getValue(), ","))
                        .collect(Collectors.toList());
        String path = request.getPath().value();

        return route.getUrl() +
                StringUtils.substring(path, StringUtils.indexOf(path, serviceName) + serviceName.length())
                + "?" +
                StringUtils.join(queryParamList, "&");
    }

    /**
     * 将请求中的cookie设置到spec中
     * @param request 请求
     * @param spec spec（WebClient内部即将发送的请求）
     */
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
    public List<String> getKeys() {
        return keys;
    }

    @Override
    public Mono<Void> invoke(String serviceName, ServerWebExchange exchange) {
        List<Route> routeList = routes.get(serviceName);

        if (routeList == null){
            throw new RequestFailedException("can not request: route not found");
        }

        // TODO 目前没有负载均衡策略，默认取第一个服务
        return invoke(serviceName, routeList.get(0), exchange);
    }
}
