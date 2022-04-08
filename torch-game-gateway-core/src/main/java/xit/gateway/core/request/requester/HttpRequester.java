package xit.gateway.core.request.requester;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Knifer
 * Description: Rest HTTP请求发送器。
 * Date: 2022/03/27
 */
public interface HttpRequester extends Requester{
    /**
     * 执行请求
     * @return 响应结果
     * @param routeName 要请求的路由
     */
    Mono<Void> invoke(String routeName, ServerWebExchange exchange);
}