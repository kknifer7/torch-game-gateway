package xit.gateway.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import xit.gateway.api.request.container.RequestContextContainer;
import xit.gateway.api.request.container.RoutesContainer;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.api.route.limiter.Limiter;
import xit.gateway.api.service.ConfigService;
import xit.gateway.pojo.Route;
import xit.gateway.utils.GatewayUriUtils;

@Component
public class LimitingFilter implements WebFilter {
    private final Limiter limiter;
    private final ConfigService configService;
    private final RequestContextContainer requestContextContainer;
    private final RoutesContainer routesContainer;
    private final RouteAccessor routeAccessor;
    private final Logger logger = LoggerFactory.getLogger(LimitingFilter.class);
    private boolean enableFusingOnLimiting;

    @Autowired
    public LimitingFilter(Limiter limiter, ConfigService configService, RequestContextContainer requestContextContainer, RoutesContainer routesContainer, RouteAccessor routeAccessor) {
        this.limiter = limiter;
        this.configService = configService;
        this.requestContextContainer = requestContextContainer;
        this.routesContainer = routesContainer;
        this.routeAccessor = routeAccessor;
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
        if (limiter.tryAcquire()){
            return chain.filter(exchange);
        }else{
            ServerHttpResponse response = exchange.getResponse();
            String path = exchange.getRequest().getPath().toString();

            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            if (enableFusingOnLimiting && GatewayUriUtils.isUsingService(path)){
                // 正在使用服务并且允许限流熔断
                String serviceName = GatewayUriUtils.getServiceNameFromUri(path);
                Route routeNeedFusing = routesContainer.get(serviceName)
                        .get(requestContextContainer.get(serviceName).lastCalledIndex().get());

                if (routeNeedFusing.getStatus()){
                    // 为了线程安全，此处不直接设置routeNeedFusing的status，通过accessor来做
                    routeAccessor.disableRoute(serviceName, routeNeedFusing.getId());
                    logger.warn("[{}]（id={}）已被熔断", routeNeedFusing.getName(), routeNeedFusing.getId());
                }
            }

            return response.setComplete();
        }
    }
}
