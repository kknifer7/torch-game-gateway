package xit.gateway.api.service;

import xit.gateway.pojo.Route;

import java.util.List;

public interface RouteService {
    void addRoutesFromRedis(String serviceId);
    void addRouteFromRedis(String routeId);
    void updateRouteFromRedis(String routeId);
    void save(Route route);
    void saveList(List<Route> routeList);
    void remove(Route route);
    void removeByService(String serviceId);
    void disableRoute(String serviceId, String routeId);
}
