package xit.gateway.core.route.container.impl;

import org.springframework.stereotype.Component;
import xit.gateway.core.route.container.RouteGroupContainer;
import xit.gateway.utils.UUIDUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class GlobalRouteGroupContainer implements RouteGroupContainer {
    private final Map<String, RouteGroup> routeGroupMap;

    public GlobalRouteGroupContainer(){
        this.routeGroupMap = new ConcurrentHashMap<>();
    }

    @Override
    public void put(RouteGroup routeGroup) {
        handleRouteGroupDuplicate(routeGroup);
        routeGroupMap.put(routeGroup.getId(), routeGroup);
    }

    @Override
    public void putAll(List<RouteGroup> routeGroupList) {
        routeGroupMap.putAll(
                routeGroupList.stream()
                        .peek(this::handleRouteGroupDuplicate)
                        .collect(Collectors.toMap(RouteGroup::getId, routeGroup -> routeGroup))
        );
    }

    @Override
    public RouteGroup get(String id) {
        return routeGroupMap.get(id);
    }

    @Override
    public boolean contains(String id) {
        return routeGroupMap.containsKey(id);
    }

    @Override
    public boolean isEmpty() {
        return routeGroupMap.isEmpty();
    }

    @Override
    public void handleRouteGroupDuplicate(RouteGroup routeGroup) {
        String id = routeGroup.getId();

        if (id == null || routeGroupMap.containsKey(id)){
            id = UUIDUtils.getRandom();
            routeGroup.setId(id);
        }
    }
}
