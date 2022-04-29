package xit.gateway.api.service;

public interface RouteService {
    void addRoutesFromRedis(String serviceId);
    void addRouteFromRedis(String routeId);
    void updateRouteFromRedis(String routeId);
    void disableRoute(String serviceId, String routeId);
}
