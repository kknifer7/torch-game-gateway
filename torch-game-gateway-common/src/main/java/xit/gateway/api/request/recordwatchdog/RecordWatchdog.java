package xit.gateway.api.request.recordwatchdog;

import xit.gateway.pojo.RequesterProxyResult;

/**
 * @author Knifer
 * Description: 监控并发送代理请求的执行情况
 * Date: 2022/04/17
 */
public interface RecordWatchdog {
    void watchAndSend(RequesterProxyResult proxyResult);
}
