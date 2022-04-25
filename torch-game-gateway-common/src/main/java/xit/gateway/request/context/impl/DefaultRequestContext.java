package xit.gateway.request.context.impl;

import xit.gateway.api.request.context.RequestContext;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultRequestContext implements RequestContext {
    private final AtomicInteger lastCalledIndex;

    public DefaultRequestContext() {
        this.lastCalledIndex = new AtomicInteger(0);
    }

    @Override
    public AtomicInteger lastCalledIndex() {
        return lastCalledIndex;
    }
}
