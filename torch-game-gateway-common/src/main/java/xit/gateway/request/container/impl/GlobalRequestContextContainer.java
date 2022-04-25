package xit.gateway.request.container.impl;

import xit.gateway.api.request.container.RequestContextContainer;
import xit.gateway.api.request.context.RequestContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalRequestContextContainer implements RequestContextContainer {
    private final Map<String, RequestContext> map = new ConcurrentHashMap<>();

    @Override
    public void put(String requesterKey, RequestContext rc) {
        map.put(requesterKey, rc);
    }

    @Override
    public boolean contains(String key) {
        return map.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public RequestContext get(String key) {
        return map.get(key);
    }
}
