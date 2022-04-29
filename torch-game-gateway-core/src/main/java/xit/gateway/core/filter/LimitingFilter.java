package xit.gateway.core.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import xit.gateway.api.jwt.AuthTokenHandler;
import xit.gateway.api.request.container.RequestContextContainer;
import xit.gateway.api.request.container.RoutesContainer;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.api.route.limiter.Limiter;
import xit.gateway.api.service.ConfigService;
import xit.gateway.constant.ResultCode;
import xit.gateway.core.limiter.container.impl.GlobalLimiterContainer;
import xit.gateway.core.limiter.impl.DefaultLimiter;
import xit.gateway.utils.GatewayUriUtils;
import xit.gateway.utils.RIUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LimitingFilter implements WebFilter {
    private final ConfigService configService;
    private final RequestContextContainer requestContextContainer;
    private final RoutesContainer routesContainer;
    private final RouteAccessor routeAccessor;
    private final GlobalLimiterContainer limiterContainer;
    private boolean enableFusingOnLimiting;
    private final AuthTokenHandler tokenHandler;
    // （用户id=服务名称）用来存储用户和服务的熔断关系的map
    private final Map<Long, String> userAndServiceNameMapForFusing = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(LimitingFilter.class);

    @Autowired
    public LimitingFilter(ConfigService configService, RequestContextContainer requestContextContainer, RoutesContainer routesContainer, RouteAccessor routeAccessor, GlobalLimiterContainer limiterContainer, AuthTokenHandler tokenHandler) {
        this.configService = configService;
        this.requestContextContainer = requestContextContainer;
        this.routesContainer = routesContainer;
        this.routeAccessor = routeAccessor;
        this.limiterContainer = limiterContainer;
        this.tokenHandler = tokenHandler;
        flush();
    }

    public void flush(){
        configService.get("enable_fusing_on_limiting")
                .flatMap(eflol -> {
                    enableFusingOnLimiting = Boolean.parseBoolean(eflol);

                    return Mono.empty();
                }).subscribe();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if (GatewayUriUtils.matchLogin(path)){
            return chain.filter(exchange);
        }

        ServerHttpResponse response = exchange.getResponse();
        Long userId = tokenHandler.parse(
                exchange.getRequest()
                        .getHeaders()
                        .get("AUTH-TOKEN")
                        .get(0)
        ).getId();
        Limiter userLimiter = limiterContainer.get(userId);
        DefaultLimiter routeLimiter;

        if (GatewayUriUtils.isUsingService(path)){
            String serviceName = GatewayUriUtils.getServiceNameFromUri(path);

            if (StringUtils.equals(userAndServiceNameMapForFusing.get(userId), serviceName)){
                return RIUtils.send(response, ResultCode.FORBIDDEN, "服务暂不可用，因为当前用户请求过多", null);
            }
            if (enableFusingOnLimiting){
                routeLimiter = (DefaultLimiter) limiterContainer.get(routesContainer.get(serviceName)
                        .get(requestContextContainer.get(serviceName).lastCalledIndex().get()).getId());
                if (limiting(userLimiter)){ // 针对用户
                    response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    userAndServiceNameMapForFusing.put(userId, serviceName);
                    logger.warn("[serviceName={}]-[userId={}]  已熔断，因为这个用户请求过多", serviceName, userId);

                    return response.setComplete();
                }
                if (limiting(routeLimiter)){    // 针对接口
                    response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    routeAccessor.disableRoute(serviceName, routeLimiter.getId().toString());
                    logger.warn("[serviceName={}]-[routeId={}]  已熔断，因为这个路由请求过多", serviceName, routeLimiter.getId());

                    return response.setComplete();
                }
            }
        }

        return chain.filter(exchange);
    }

    private boolean limiting(Limiter limiter){
        return limiter != null && !limiter.tryAcquire();
    }
}
