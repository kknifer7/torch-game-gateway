package xit.gateway.core.request.requester.context.impl;

import xit.gateway.core.request.requester.context.RequesterContext;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultRequesterContext implements RequesterContext {
    private final AtomicInteger lastCalledIndex;

    public DefaultRequesterContext() {
        this.lastCalledIndex = new AtomicInteger(0);
    }

    @Override
    public AtomicInteger lastCalledIndex() {
        return lastCalledIndex;
    }
}
