package xit.gateway.core.request.requester;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Knifer
 * Description: RPC请求发送器。
 * Date: 2022/03/27
 */
public interface RpcRequester extends Requester{
    Mono<Void> invoke(String routeName, ServerWebExchange exchange);
}
