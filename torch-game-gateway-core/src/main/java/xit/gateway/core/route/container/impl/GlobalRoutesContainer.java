package xit.gateway.core.route.container.impl;

import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import xit.gateway.api.request.container.RoutesContainer;
import xit.gateway.pojo.Route;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Knifer
 * Description: 全局路由容器
 * Date: 2022/04/22
 */
@Component
public class GlobalRoutesContainer implements RoutesContainer {
    private final ConcurrentHashMap<String, List<Route>> map;

    public GlobalRoutesContainer() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void put(Route route) {
        String serviceId = route.getServiceName();
        List<Route> routes = map.get(serviceId);

        if (routes != null){
            routes.add(route);
        }else{
            map.put(serviceId, new CopyOnWriteArrayList<>(List.of(route)));
        }
    }

    @Override
    public void putAll(List<Route> routeList) {
        routeList.forEach(this::put);
    }

    @Override
    public boolean contains(Route route) {
        if (map.isEmpty())
            return false;

        List<Route> routes = map.get(route.getServiceName());

        return !Collections.isEmpty(routes) &&
                routes.stream()
                        .anyMatch(r -> StringUtils.equals(r.getId(), route.getId()));
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public List<Route> get(Serializable serviceId) {
        return map.get(serviceId);
    }

    @Override
    public List<Route> remove(Serializable key) {
        return map.remove(key);
    }

    @Override
    public Set<Map.Entry<String, List<Route>>> entrySet() {
        return map.entrySet();
    }
}
