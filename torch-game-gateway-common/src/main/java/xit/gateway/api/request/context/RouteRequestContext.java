package xit.gateway.api.request.context;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Knifer
 * Description: 请求器上下文
 * Date: 2022/04/10
 */
public interface RouteRequestContext {
    AtomicInteger lastCalledIndex();
}
