package xit.gateway.core.route.container;

import xit.gateway.pojo.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Knifer
 * Description: 路由容器
 * Date: 2022/03/27
 */
public abstract class AbstractRouteContainer implements RouteContainer {
    protected final Map<String, List<Route>> routeMap;

    protected AbstractRouteContainer(){
        routeMap = new ConcurrentHashMap<>();
    }

    @Override
    public void put(Route route) {
        List<Route> routes = routeMap.get(route.getId());

        if (routes == null){
            routeMap.put(route.getId(), new ArrayList<>());
        }else{
            routes.add(route);
        }
    }

    @Override
    public void putAll(List<Route> routeList){
        for (Route r : routeList){
            this.put(r);
        }
    }

    @Override
    public List<Route> get(String routeId) {
        return routeMap.get(routeId);
    }
}
