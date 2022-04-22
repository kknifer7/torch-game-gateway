package xit.gateway.core.route.container;

import org.springframework.stereotype.Component;
import xit.gateway.core.container.MultiContainer;
import xit.gateway.core.pojo.Route;

import java.util.Arrays;
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
public class GlobalRoutesContainer implements MultiContainer<Route> {
    private final ConcurrentHashMap<String, List<Route>> map;

    public GlobalRoutesContainer() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public void put(Route route) {
        String serviceId = route.getName();
        List<Route> routes = map.get(route.getName());

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
    public boolean contains(String serviceId) {
        return map.containsKey(serviceId);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public List<Route> get(String serviceId) {
        return map.get(serviceId);
    }

    @Override
    public Set<Map.Entry<String, List<Route>>> entrySet() {
        return map.entrySet();
    }
}
