package xit.gateway.api.route.limiter.manager;

import xit.gateway.pojo.Limiter;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface LimiterManager {
    void flush();
    void addLimiter(Serializable id);
    void removeLimiter(Serializable id);
    void addLimiter(Serializable userId, Long limit, Long limitingTimeout, TimeUnit limitingTimeUnit);
    List<Limiter> getAllLimiterVO();
}