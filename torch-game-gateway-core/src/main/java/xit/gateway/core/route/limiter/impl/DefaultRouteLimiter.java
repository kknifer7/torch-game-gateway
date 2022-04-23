package xit.gateway.core.route.limiter.impl;

import com.google.common.util.concurrent.RateLimiter;
import xit.gateway.api.route.limiter.AbstractRouteLimiter;
import xit.gateway.pojo.Route;

public class DefaultRouteLimiter extends AbstractRouteLimiter {
    private RateLimiter rateLimiter;
    private String timeUnit;
    private double permitsPerUnit;

    public DefaultRouteLimiter(Route route) {
        super(route);
        this.rateLimiter = null;
    }

    @Override
    public boolean acquire() {
        return false;
    }
}
