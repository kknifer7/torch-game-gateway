package xit.gateway.api.route.limiter;

import java.io.Serializable;

/**
 * @author  Knifer
 * Date: 2022/04/29
 * Description: 令牌桶
 */
public interface Limiter {
    // 尝试获取令牌
    boolean tryAcquire();
    // 设置limit
    void setLimit(long limit);
    // 获取id
    Serializable getId();
}
