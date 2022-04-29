package xit.gateway.api.route.limiter.manager;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public interface LimiterManager {
    void flush();
    void addLimiter(Serializable id);
    void removeLimiter(Serializable id);
    void addLimiter(Serializable userId, Long limit, Long limitingTimeout, TimeUnit limitingTimeUnit);
}