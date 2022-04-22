package xit.gateway.core.request.requester;

import org.springframework.web.server.ServerWebExchange;
import xit.gateway.core.pojo.Route;
import xit.gateway.core.pojo.RequesterProxyResult;
import xit.gateway.core.request.requester.impl.DefaultHttpRequester;
import xit.gateway.core.request.requester.impl.DefaultRpcRequester;

import java.net.UnknownHostException;

/**
 * @author Knifer
 * Description: 请求发送器。
 * Date: 2022/03/27
 */
public interface Requester {
    static Requester get(Route route) {
        Requester res = null;

        switch (route.getProtocol()){
            case HTTP:
                res = new DefaultHttpRequester(route);
                break;
            case RPC:
                res = new DefaultRpcRequester(route);
                break;
        }

        return res;
    }

    /**
     * 执行请求
     * @return 响应结果
     */
    RequesterProxyResult invoke(ServerWebExchange exchange) throws UnknownHostException;

    /**
     * 获取能够索引到这个Requester的所有Key（一般为requester中routeGroup下所有路由的名称）
     * @return key
     */
    String getKeyInContainer();
}
