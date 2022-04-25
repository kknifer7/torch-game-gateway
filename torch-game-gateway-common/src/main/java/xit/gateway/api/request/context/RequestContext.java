package xit.gateway.api.request.context;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Knifer
 * Description: 请求上下文，用于负载均衡
 * Date: 2022/04/10
 */
public interface RequestContext {
    AtomicInteger lastCalledIndex();
}
