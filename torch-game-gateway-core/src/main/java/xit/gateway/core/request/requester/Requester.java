package xit.gateway.core.request.requester;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.CorePublisher;

import java.util.List;

public interface Requester {
    /**
     * 执行请求
     * @return 响应结果
     * @param routeName 要请求的路由名
     */
    CorePublisher<?> invoke(String routeName, ServerWebExchange exchange);

    /**
     * 获取能够索引到这个Requester的所有Key（一般为requester中routeGroup下所有路由的名称）
     * @return keys
     */
    List<String> getKeys();
}
