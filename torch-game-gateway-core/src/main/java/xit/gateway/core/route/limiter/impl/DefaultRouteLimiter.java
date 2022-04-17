package xit.gateway.core.route.limiter.impl;

import xit.gateway.core.route.limiter.AbstractRouteLimiter;
import xit.gateway.pojo.Route;

public class DefaultRouteLimiter extends AbstractRouteLimiter {
    public DefaultRouteLimiter(Route route) {
        super(route);
    }

    @Override
    public boolean acquire() {
        return false;
    }
}
