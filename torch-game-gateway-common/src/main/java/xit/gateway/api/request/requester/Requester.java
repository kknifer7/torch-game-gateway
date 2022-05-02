package xit.gateway.api.request.requester;

import org.springframework.web.server.ServerWebExchange;
import xit.gateway.pojo.RequesterProxyResult;

import java.net.UnknownHostException;

/**
 * @author Knifer
 * Description: 请求发送器。
 * Date: 2022/03/27
 */
public interface Requester {
    /**
     * 执行请求
     * @return 响应结果
     */
    RequesterProxyResult invoke(ServerWebExchange exchange) throws UnknownHostException;

    /**
     * 获取能够索引到这个Requester的所有Key（一般为serviceName）
     * @return key
     */
    String getKeyInContainer();
}
