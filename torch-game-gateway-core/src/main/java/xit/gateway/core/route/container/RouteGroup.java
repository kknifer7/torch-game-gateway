package xit.gateway.core.route.container;

import xit.gateway.api.container.MultiContainer;
import xit.gateway.exception.route.RouteResolvingException;
import xit.gateway.pojo.HttpRoute;
import xit.gateway.pojo.Route;
import xit.gateway.pojo.RpcRoute;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Knifer
 * Description: 路由组（聚合管理路由条目）。
 * Date: 2022/03/27
 */
public class RouteGroup implements MultiContainer<Route> {
    private String id;
    private String name;
    private String baseUrl;
    private ConcurrentHashMap<String, List<Route>> httpRoutes;
    private ConcurrentHashMap<String, List<Route>> rpcRoutes;

    public RouteGroup() {
    }

    public RouteGroup(String id, String name, String baseUrl, ConcurrentHashMap<String, List<Route>> httpRoutes, ConcurrentHashMap<String, List<Route>> rpcRoutes) {
        this.id = id;
        this.name = name;
        this.baseUrl = baseUrl;
        this.httpRoutes = httpRoutes;
        this.rpcRoutes = rpcRoutes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map<String, List<Route>> getHttpRoutes() {
        return httpRoutes;
    }

    public void setHttpRoutes(ConcurrentHashMap<String, List<Route>> httpRoutes) {
        this.httpRoutes = httpRoutes;
    }

    public Map<String, List<Route>> getRpcRoutes() {
        return rpcRoutes;
    }

    public void setRpcRoutes(ConcurrentHashMap<String, List<Route>> rpcRoutes) {
        this.rpcRoutes = rpcRoutes;
    }

    @Override
    public String toString() {
        return "RouteGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", httpRoutes=" + httpRoutes +
                ", rpcRoutes=" + rpcRoutes +
                '}';
    }

    @Override
    public void put(Route route) {
        List<Route> routes;
        Map<String, List<Route>> routeMap;

        if (route instanceof HttpRoute){
            routeMap = httpRoutes;
        }else if (route instanceof RpcRoute){
            routeMap = rpcRoutes;
        }else{
            throw new RouteResolvingException("unknown route type");
        }

        routes = routeMap.get(route.getId());
        if (routes == null){
            routes = new CopyOnWriteArrayList<>();
            routes.add(route);
            routeMap.put(route.getId(), routes);
        }else{
            routes.add(route);
        }
    }

    @Override
    public void putAll(List<Route> routeList) {
        routeList.forEach(this::put);
    }

    @Override
    public List<Route> get(String routeId) {
        List<Route> result = httpRoutes.get(routeId);

        if (result == null){
            result = rpcRoutes.get(routeId);
        }

        return result;
    }

    @Override
    public Set<Map.Entry<String, List<Route>>> entrySet() {
        return null;
    }

    @Override
    public boolean contains(String primaryKey) {
        return httpRoutes.containsKey(primaryKey) || rpcRoutes.containsKey(primaryKey);
    }

    @Override
    public boolean isEmpty() {
        return httpRoutes.isEmpty() && rpcRoutes.isEmpty();
    }
}
