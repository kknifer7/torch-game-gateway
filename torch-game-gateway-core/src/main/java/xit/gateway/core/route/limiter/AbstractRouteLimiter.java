package xit.gateway.core.route.limiter;

import xit.gateway.core.pojo.Route;

public abstract class AbstractRouteLimiter implements RouteLimiter{
    protected final Route route;

    public AbstractRouteLimiter(Route route){
        this.route = route;
    }
}
