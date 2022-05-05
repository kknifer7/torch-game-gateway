package xit.gateway.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xit.gateway.api.request.container.RoutesContainer;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.api.service.RouteService;
import xit.gateway.constant.ProtocolType;
import xit.gateway.constant.RedisKey;
import xit.gateway.pojo.Route;
import xit.gateway.utils.RedisUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class RouteServiceImpl implements RouteService {
    private final RouteAccessor accessor;
    private final RoutesContainer container;

    @Autowired
    public RouteServiceImpl(RouteAccessor accessor, RoutesContainer container) {
        this.accessor = accessor;
        this.container = container;
    }

    @Override
    public void addRoutesFromRedis(String serviceId) {
        Set<Route> routes = RedisUtils.keys(RedisKey.ROUTE_LIST.extend(serviceId), Route.class);

        accessor.loadRoutes(routes);
    }

    @Override
    public void addRouteFromRedis(String routeId) {
        Route route = RedisUtils.get(RedisKey.ROUTE.extend(routeId), Route.class);

        accessor.loadRoutes(Collections.singletonList(route));
    }

    @Override
    public void updateRouteFromRedis(String routeId) {
        Route route = RedisUtils.get(RedisKey.ROUTE.extend(routeId), Route.class);

        accessor.updateRoute(route);
    }

    @Override
    public void save(Route route) {
        if (container.contains(route)){
            if (route.getProtocol() == ProtocolType.RPC){
                // 如果是RPC协议，先移除，再重新添加
                accessor.removeRoute(route.getServiceName(), route.getId());
                accessor.loadRoute(route);
            }else{
                accessor.updateRoute(route);
            }
        }else{
            accessor.loadRoute(route);
        }
    }

    @Override
    public void saveList(List<Route> routeList) {
        accessor.loadRoutes(routeList);
    }

    @Override
    public void remove(Route route) {
        accessor.removeRoute(route.getServiceName(), route.getId());
    }

    @Override
    public void removeByService(String serviceId) {
        accessor.removeAllRoute(serviceId);
    }

    @Override
    public void disableRoute(String serviceId, String routeId) {
        accessor.disableRoute(serviceId, routeId);
    }
}
