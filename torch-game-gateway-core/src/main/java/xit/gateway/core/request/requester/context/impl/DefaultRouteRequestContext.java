package xit.gateway.core.request.requester.context.impl;

import xit.gateway.api.request.context.RouteRequestContext;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultRouteRequestContext implements RouteRequestContext {
    private final AtomicInteger lastCalledIndex;

    public DefaultRouteRequestContext() {
        this.lastCalledIndex = new AtomicInteger(0);
    }

    @Override
    public AtomicInteger lastCalledIndex() {
        return lastCalledIndex;
    }
}
