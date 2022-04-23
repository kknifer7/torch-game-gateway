package xit.gateway.api.route.limiter;

/**
 * @author Knifer
 * Description: 单路由限流器
 * Date: 2022/04/17
 */
public interface RouteLimiter {
    boolean acquire();
}
