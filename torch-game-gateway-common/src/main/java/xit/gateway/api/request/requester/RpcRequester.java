package xit.gateway.api.request.requester;

/**
 * @author Knifer
 * Description: RPC请求发送器。
 * Date: 2022/03/27
 */
public interface RpcRequester extends Requester{
    void close();
}
