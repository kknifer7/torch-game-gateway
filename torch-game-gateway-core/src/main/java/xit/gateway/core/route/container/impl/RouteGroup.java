package xit.gateway.core.route.container.impl;

import xit.gateway.core.route.container.RouteContainer;
import xit.gateway.pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Knifer
 * Description: 路由组（聚合管理路由条目）。
 * Date: 2022/03/27
 */
public class RouteGroup implements RouteContainer {
    private String id;
    private String name;
    private String baseUrl;
    private Map<String, List<Route>> httpRoutes;
    private Map<String, List<Route>> rpcRoutes;

    public RouteGroup() {
    }

    public RouteGroup(String id, String name, String baseUrl, Map<String, List<Route>> httpRoutes, Map<String, List<Route>> rpcRoutes) {
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

    public void setHttpRoutes(Map<String, List<Route>> httpRoutes) {
        this.httpRoutes = httpRoutes;
    }

    public Map<String, List<Route>> getRpcRoutes() {
        return rpcRoutes;
    }

    public void setRpcRoutes(Map<String, List<Route>> rpcRoutes) {
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

        if (route instanceof HttpRoute){
            routes = httpRoutes.get(route.getName());
            if (routes == null){
                routes = new ArrayList<>();
                routes.add(route);
                httpRoutes.put(route.getName(), routes);
            }else{
                routes.add(route);
            }
        }else if (route instanceof RpcRoute){
            routes = rpcRoutes.get(route.getName());
            if (routes == null){
                routes = new ArrayList<>();
                routes.add(route);
                rpcRoutes.put(route.getName(), routes);
            }else{
                routes.add(route);
            }
        }
    }

    @Override
    public void putAll(List<Route> routeList) {
        routeList.forEach(this::put);
    }

    @Override
    public List<Route> get(String routeName) {
        List<Route> result = httpRoutes.get(routeName);

        if (result == null){
            result = rpcRoutes.get(routeName);
        }

        return result;
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
