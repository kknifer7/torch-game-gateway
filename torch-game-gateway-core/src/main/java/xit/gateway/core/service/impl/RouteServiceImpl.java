package xit.gateway.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.api.service.RouteService;
import xit.gateway.constant.RedisKey;
import xit.gateway.pojo.Route;
import xit.gateway.utils.RedisUtils;

import java.util.Collections;
import java.util.Set;

@Service
public class RouteServiceImpl implements RouteService {
    private final RouteAccessor accessor;

    @Autowired
    public RouteServiceImpl(RouteAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public void addRoutesFromRedis(String serviceId) {
        Set<Route> routes = RedisUtils.keys(RedisKey.ROUTE_LIST.extend(serviceId), Route.class);

        accessor.loadRoutes(routes);
    }

    @Override
    public void addRouteFromRedis(String routeId) {
        // TODO 获取最新Route
        Route route = RedisUtils.get(RedisKey.ROUTE.extend(routeId), Route.class);

        accessor.loadRoutes(Collections.singletonList(route));
    }

    @Override
    public void updateRouteFromRedis(String routeId) {
        Route route = RedisUtils.get(RedisKey.ROUTE.extend(routeId), Route.class);

        accessor.updateRoute(route);
    }

    @Override
    public void disableRoute(String serviceId, String routeId) {
        accessor.disableRoute(serviceId, routeId);
    }
}
