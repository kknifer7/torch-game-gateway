package xit.gateway.core.request.requester.impl;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import xit.gateway.core.request.requester.ssl.KeyStoreHolder;
import xit.gateway.exception.route.RouteDisabledException;
import xit.gateway.api.request.requester.AbstractRequester;
import xit.gateway.api.request.requester.HttpRequester;
import xit.gateway.exception.system.SystemException;
import xit.gateway.pojo.*;

import javax.net.ssl.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Knifer
 * Description: HTTP REST 请求器实现。
 * Date: 2022/03/27
 */
public class DefaultHttpRequester extends AbstractRequester implements HttpRequester {
    private final Logger logger = LoggerFactory.getLogger(DefaultHttpRequester.class);
    private final WebClient webClient;

    public DefaultHttpRequester(Route route) {
        super(route);
        switch (route.getProtocol()){
            case HTTP:
                this.webClient = WebClient.create(route.getUrl());
                break;
            case HTTPS:
                this.webClient = WebClient.builder()
                        .clientConnector(KeyStoreHolder.hasKeyStore() ?
                                getClientConnectorWithKeyStore() : getClientConnector())
                        .baseUrl(route.getUrl())
                        .build();
                break;
            default:
                throw new SystemException("创建WebClient失败，协议不合法");
        }
    }

    @Override
    public RequesterProxyResult invoke(ServerWebExchange exchange) {
        if (!route.getStatus()){
            throw new RouteDisabledException("服务暂不可用", exchange.getRequest().getPath().value());
        }

        Mono<Void> result;
        long startTime;
        RequesterProxyResult proxyResult = new RequesterProxyResult();
        WebClient.RequestHeadersSpec<?> spec;
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        CallLog.Builder callRecordBuilder = CallLog.builder()
                .gatewayHost(request.getLocalAddress().getHostName())
                .gatewayPort(String.valueOf(request.getLocalAddress().getPort()))
                .gatewayUri(request.getPath().value())
                .serviceId(route.getServiceName())
                .timestamp(System.currentTimeMillis())
                .route(() -> {
                    CalledRoute calledRoute = new CalledRoute();
                    BeanUtils.copyProperties(route, calledRoute);

                    return calledRoute;
                });
        logger.info("调用服务：" + route.getRemark());
        spec = webClient.method(request.getMethod())
                .uri(resolveServiceUri(request))
                .headers((headers) -> {
                    headers.putAll(request.getHeaders());
                    headers.remove("HOST"); // 由于是MultiMap，所以不能直接add覆盖，要先删除原值
                    headers.add("HOST", route.getHost());
                })
                .body(BodyInserters.fromDataBuffers(request.getBody()));
        transformCookiesToSpec(request, spec);

        // 调用、处理响应结果或异常
        startTime = System.currentTimeMillis();
        result = spec.exchangeToMono(proxyResponse -> {
            callRecordBuilder.callTime(System.currentTimeMillis() - startTime)
                    .success(true);
            proxyResult.setCompleted(true);
            prepareResponse(response, proxyResponse);
            return response.writeWith(proxyResponse.bodyToMono(DataBuffer.class));
        }).onErrorResume(ex -> Mono.defer(() -> {
            callRecordBuilder.callTime(System.currentTimeMillis() - startTime)
                    .success(false);
            proxyResult.setCompleted(true);
            String errorMsg = "服务调用失败：" + ex.getLocalizedMessage();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMsg.getBytes())));
        }).then(Mono.empty()));
        proxyResult.setCallRecord(callRecordBuilder.build());
        proxyResult.setResult(result);

        return proxyResult;
    }

    /**
     * 解析出访问目标服务的路径和url上的参数
     * @param request 请求
     * @return path
     */
    private String resolveServiceUri(ServerHttpRequest request){
        List<String> queryParamList =
                request.getQueryParams().entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + "=" + StringUtils.join(entry.getValue(), ","))
                        .collect(Collectors.toList());
        String path = request.getPath().value();
        String serviceId = route.getServiceName();

        return StringUtils.substring(path, StringUtils.indexOf(path, serviceId) + serviceId.length())
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

    private ReactorClientHttpConnector getClientConnector(){
        HttpClient secure = HttpClient.create()
                .secure(t -> t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));

        return new ReactorClientHttpConnector(secure);
    }

    private ReactorClientHttpConnector getClientConnectorWithKeyStore(){
        SslContextBuilder builder = SslContextBuilder.forClient();
        KeyManagerFactory keyManagerFactory = null;
        try {
            keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(KeyStoreHolder.getKeyStore(), KeyStoreHolder.getPassword());
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e) {
            e.printStackTrace();
        }
        builder.keyManager(keyManagerFactory);

        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(KeyStoreHolder.getKeyStore());
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        builder.trustManager(trustManagerFactory);

        SslContext sslContext = null;
        try {
            sslContext = builder.build();
        } catch (SSLException e) {
            e.printStackTrace();
        }

        SslContext finalSslContext = sslContext;
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(finalSslContext).handlerConfigurator(handler -> {
            SSLEngine engine = handler.engine();
            List<SNIMatcher> matchers = new LinkedList<>();
            SNIMatcher matcher = new SNIMatcher(0) {
                @Override
                public boolean matches(SNIServerName serverName) {
                    return true;
                }
            };
            matchers.add(matcher);
            SSLParameters params = new SSLParameters();
            params.setSNIMatchers(matchers);
            engine.setSSLParameters(params);
        }));

        return new ReactorClientHttpConnector(httpClient);
    }
}
