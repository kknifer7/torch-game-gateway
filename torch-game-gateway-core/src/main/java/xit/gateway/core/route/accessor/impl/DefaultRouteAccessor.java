package xit.gateway.core.route.accessor.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xit.gateway.api.request.container.RequesterContainer;
import xit.gateway.api.request.container.RequestContextContainer;
import xit.gateway.api.request.container.RoutesContainer;
import xit.gateway.api.context.GatewayContext;
import xit.gateway.request.context.impl.DefaultRequestContext;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.core.request.requester.factory.RequesterFactory;
import xit.gateway.exception.system.SystemException;
import xit.gateway.pojo.Route;

import java.util.List;

@Component
public class DefaultRouteAccessor implements RouteAccessor {
    private final RequesterContainer globalRequesterContainer;
    private final RoutesContainer globalRoutesContainer;
    private final RequestContextContainer globalRouteRequestContextContainer;

    @Autowired
    public DefaultRouteAccessor(GatewayContext context) {
        this.globalRequesterContainer = context.requesterContainer();
        this.globalRoutesContainer = context.routesContainer();
        this.globalRouteRequestContextContainer = context.routeRequestContextContainer();
    }

    @Override
    public void loadRoutes(List<Route> routeList) {
        routeList.forEach(route -> {
            globalRoutesContainer.put(route);
            globalRequesterContainer.put(RequesterFactory.get(route));
            globalRouteRequestContextContainer.put(route.getName(), new DefaultRequestContext());
        });
    }

    @Override
    public void updateRoute(Route route) {
        // TODO 开发专门的路由降级/熔断 API
        List<Route> routes = globalRoutesContainer.get(route.getName());
        Route oldRoute;

        if (CollectionUtils.isEmpty(routes)){
            // 路由所属的服务不存在
            throw new SystemException("route need update's service not exist");
        }
        oldRoute = routes.stream()
                .findFirst()
                .orElseThrow(() -> new SystemException("route doesn't exist"));
        synchronized (this){
            BeanUtils.copyProperties(route, oldRoute);
        }
    }

    @Override
    public void disableRoute(String serviceId, String routeId) {
        List<Route> routes = globalRoutesContainer.get(serviceId);
        Route oldRoute;

        if (CollectionUtils.isEmpty(routes)){
            // 路由所属的服务不存在
            throw new SystemException("route need update's service not exist");
        }
        oldRoute = routes.stream()
                .findFirst()
                .orElseThrow(() -> new SystemException("route doesn't exist"));
        synchronized (this){
            oldRoute.setDisabled(true);
        }
    }
}
