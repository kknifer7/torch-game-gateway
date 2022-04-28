package xit.gateway.api.service;

public interface RouteService {
    void addRoutesFromRedis(String serviceId);
    void addRouteFromRedis(String serviceId, String routeId);
    void updateRouteFromRedis(String serviceId, String routeId);
    void disableRoute(String serviceId, String routeId);
}
