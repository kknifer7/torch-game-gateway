package xit.gateway.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.core.request.requester.Requester;
import xit.gateway.exception.requester.RequestFailedException;
import xit.gateway.exception.requester.RequesterNotFoundException;
import xit.gateway.pojo.ResultMessage;
import xit.gateway.utils.ResultMessageUtils;

/**
 * @author Knifer
 * Description: 负责处理代理任务。
 * Date: 2022/04/13
 */
@RestController
public class RoutingController {
    private final GlobalRequesterContainer requesterContainer;

    @Autowired
    public RoutingController(GlobalRequesterContainer requesterContainer) {
        this.requesterContainer = requesterContainer;
    }

    @RequestMapping("/before/{serviceId}/**")
    public Mono<?> all(@PathVariable("serviceId") String serviceId, ServerWebExchange exchange) {
        Requester requester = requesterContainer.get(serviceId);

        if (requester == null){
            throw new RequesterNotFoundException(
                    "服务未找到",
                    exchange.getRequest().getPath().value()
            );
        }

        return requester.invoke(serviceId, exchange);
    }

    @RequestMapping("/test")
    public Mono<String> test(){
        return Mono.just("test");
    }
}
