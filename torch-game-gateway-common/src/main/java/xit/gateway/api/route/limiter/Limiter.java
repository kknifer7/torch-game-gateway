package xit.gateway.api.route.limiter;

/**
 * @author  Knifer
 * Date: 2022/04/29
 * Description: 令牌桶
 */
public interface Limiter {
    // 刷新配置
    void flush();
    // 尝试获取令牌
    boolean tryAcquire();
}
