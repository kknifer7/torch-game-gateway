package xit.gateway.core.limiter.impl;

import com.google.common.util.concurrent.RateLimiter;
import xit.gateway.api.route.limiter.Limiter;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class DefaultLimiter implements Limiter {
    private Serializable id;
    private long limitingTimeout;
    private TimeUnit limitingTimeUnit;
    private RateLimiter rateLimiter;

    public DefaultLimiter(){}

    public DefaultLimiter(Serializable id, long limit, long limitingTimeout, TimeUnit limitingTimeUnit) {
        this.id = id;
        this.limitingTimeout = limitingTimeout;
        this.limitingTimeUnit = limitingTimeUnit;
        this.rateLimiter = RateLimiter.create(limit);
    }

    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire(limitingTimeout, limitingTimeUnit);
    }

    @Override
    public void setLimit(long limit) {
        this.rateLimiter.setRate(limit);
    }

    @Override
    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public long getLimitingTimeout() {
        return limitingTimeout;
    }

    public void setLimitingTimeout(long limitingTimeout) {
        this.limitingTimeout = limitingTimeout;
    }

    public TimeUnit getLimitingTimeUnit() {
        return limitingTimeUnit;
    }

    public void setLimitingTimeUnit(TimeUnit limitingTimeUnit) {
        this.limitingTimeUnit = limitingTimeUnit;
    }

    public Long getLimiterRate(){
        return new Double(rateLimiter.getRate()).longValue();
    }
}
