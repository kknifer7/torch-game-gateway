package xit.gateway.api.request.container;

import xit.gateway.api.request.context.RequestContext;

public interface RequestContextContainer {
    void put(String key, RequestContext rc);

    boolean contains(String key);

    boolean isEmpty();

    RequestContext get(String key);

    RequestContext remove(String key);
}
