package xit.gateway.api.request.container;

import xit.gateway.api.request.context.RequestContext;

public interface RequestContextContainer {
    public void put(String key, RequestContext rc);

    public boolean contains(String key);

    public boolean isEmpty();

    public RequestContext get(String key);
}
