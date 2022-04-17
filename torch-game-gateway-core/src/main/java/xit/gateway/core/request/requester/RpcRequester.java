package xit.gateway.core.request.requester;

import org.springframework.web.server.ServerWebExchange;
import xit.gateway.pojo.RequesterProxyResult;

import java.net.UnknownHostException;

/**
 * @author Knifer
 * Description: RPC请求发送器。
 * Date: 2022/03/27
 */
public interface RpcRequester extends Requester{
    RequesterProxyResult invoke(String routeName, ServerWebExchange exchange);
}
