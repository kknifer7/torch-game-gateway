package xit.gateway.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.core.context.GatewayContext;
import xit.gateway.core.pojo.Route;
import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.core.request.container.GlobalRouteRequestContextContainer;
import xit.gateway.core.request.recordwatchdog.RecordWatchdog;
import xit.gateway.core.request.requester.Requester;
import xit.gateway.core.route.accessor.RouteAccessor;
import xit.gateway.core.exception.route.RouteNotFoundException;
import xit.gateway.core.pojo.RequesterProxyResult;
import xit.gateway.core.route.container.GlobalRoutesContainer;
import xit.gateway.core.route.loadbalancer.Loadbalancer;

import java.net.UnknownHostException;

/**
 * @author Knifer
 * Description: 负责处理代理任务。
 * Date: 2022/04/13
 */
@RestController
public class RoutingController {
    private final GlobalRequesterContainer requesterContainer;
    private final GlobalRoutesContainer routesContainer;
    private final GlobalRouteRequestContextContainer routeRequestContextContainer;
    private final RecordWatchdog recordWatchdog;
    private final Loadbalancer loadbalancer;
    @Autowired
    private RouteAccessor accessor;

    @Autowired
    public RoutingController(GatewayContext context, RecordWatchdog recordWatchdog, Loadbalancer loadbalancer) {
        this.routesContainer = context.routesContainer();
        this.requesterContainer = context.requesterContainer();
        this.routeRequestContextContainer = context.routeRequestContextContainer();
        this.recordWatchdog = recordWatchdog;
        this.loadbalancer = loadbalancer;
    }

    @RequestMapping("/before/{serviceId}/**")
    public Mono<?> all(@PathVariable("serviceId") String serviceId, ServerWebExchange exchange) throws UnknownHostException {
        // 负载均衡
        Route route = loadbalancer.choose(routesContainer.get(serviceId), routeRequestContextContainer.get(serviceId));
        Requester requester;
        RequesterProxyResult result;

        if (route == null){
            throw new RouteNotFoundException(
                    "服务未找到",
                    exchange.getRequest().getPath().value()
            );
        }
        requester = requesterContainer.get(route.getId());
        result = requester.invoke(exchange);
        recordWatchdog.watchAndSend(result);

        return result.getResult();
    }

    @RequestMapping("/test")
    public Mono<String> test(){
        /*accessor.updateRoute("service-02", new Route(
            "22222", "123", "localhost", 2222, "123", true, null, null, null
        ));*/
        accessor.disableRoute("service-02", "333");
        return Mono.just("test");
    }
}
