package xit.gateway.api.container.request;

import xit.gateway.api.request.context.RouteRequestContext;

public interface RouteRequestContextContainer{
    public void put(String requesterKey, RouteRequestContext rc);

    public boolean contains(String key);

    public boolean isEmpty();

    public RouteRequestContext get(String key);
}
