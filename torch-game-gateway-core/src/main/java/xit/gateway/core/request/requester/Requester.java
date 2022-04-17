package xit.gateway.core.request.requester;

import org.springframework.web.server.ServerWebExchange;
import xit.gateway.core.request.requester.context.RequesterContext;
import xit.gateway.pojo.RequesterProxyResult;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author Knifer
 * Description: 请求发送器。
 * Date: 2022/03/27
 */
public interface Requester {
    /**
     * 执行请求
     * @return 响应结果
     * @param routeName 要请求的路由名
     */
    RequesterProxyResult invoke(String routeName, ServerWebExchange exchange);

    /**
     * 获取能够索引到这个Requester的所有Key（一般为requester中routeGroup下所有路由的名称）
     * @return keys
     */
    List<String> getKeys();

    /**
     * 获取请求器上下文
     * @return context
     */
    RequesterContext getRequesterContext();
}
