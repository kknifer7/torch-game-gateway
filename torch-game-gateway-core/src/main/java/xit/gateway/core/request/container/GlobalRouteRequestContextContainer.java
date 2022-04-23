package xit.gateway.core.request.container;

import org.springframework.stereotype.Component;
import xit.gateway.api.container.request.RouteRequestContextContainer;
import xit.gateway.api.request.context.RouteRequestContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalRouteRequestContextContainer implements RouteRequestContextContainer {
    private final Map<String, RouteRequestContext> map = new ConcurrentHashMap<>();

    @Override
    public void put(String requesterKey, RouteRequestContext rc) {
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
    public RouteRequestContext get(String key) {
        return map.get(key);
    }
}
