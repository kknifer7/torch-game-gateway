package xit.gateway.api.cluster.gateway.agent;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xit.gateway.pojo.Gateway;

/**
 * @author Knifer
 * Describe: 网关代理器（用于deacon向core发起请求）
 * Date: 2020/04/25
 */
public interface GatewayAgent {
    Mono<?> proxy(String serviceId, Gateway gateway, ServerWebExchange exchange);
    void disableRoute(Gateway gateway, String serviceId, String routeId);
}
