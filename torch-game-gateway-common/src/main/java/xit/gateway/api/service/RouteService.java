package xit.gateway.api.service;

public interface RouteService {
    void addRoutesFromRedis(String serviceId);
    void disableRoute(String serviceId, String routeId);
}
