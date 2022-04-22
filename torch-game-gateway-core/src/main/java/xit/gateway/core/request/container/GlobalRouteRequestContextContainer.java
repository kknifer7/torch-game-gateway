package xit.gateway.core.request.container;

import org.springframework.stereotype.Component;
import xit.gateway.core.request.requester.context.RouteRequestContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalRouteRequestContextContainer {
    private final Map<String, RouteRequestContext> map = new ConcurrentHashMap<>();

    public void put(String requesterKey, RouteRequestContext rc) {
        map.put(requesterKey, rc);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public RouteRequestContext get(String key) {
        return map.get(key);
    }
}
