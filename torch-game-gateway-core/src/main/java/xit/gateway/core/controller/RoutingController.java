package xit.gateway.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.core.request.container.impl.GlobalRequesterContainer;
import xit.gateway.core.request.requester.HttpRequester;
import xit.gateway.core.route.container.impl.GlobalRouteContainer;
import xit.gateway.pojo.ResultMessage;
import xit.gateway.utils.ResultMessageUtils;

@RestController
public class RoutingController {
    private final GlobalRequesterContainer requesterContainer;

    @Autowired
    public RoutingController(GlobalRequesterContainer requesterContainer, GlobalRouteContainer routeContainer) {
        this.requesterContainer = requesterContainer;
    }

    @RequestMapping("/before/{serviceId}/**")
    public Mono<?> all(@PathVariable("serviceId") String serviceId, ServerWebExchange exchange) {
        HttpRequester requester = (HttpRequester) requesterContainer.get(serviceId);

        if (requester == null){
            return Mono.just(ResultMessageUtils.create(
                    ResultMessage.ResultCode.ROUTE_NOT_FOUND,
                    "服务未找到",
                    exchange.getRequest().getPath().value()
                    )
            );
        }

        return requester.invoke(serviceId, exchange);
    }

    @RequestMapping("/test")
    public Mono<String> test(){
        return Mono.just("test");
    }
}
