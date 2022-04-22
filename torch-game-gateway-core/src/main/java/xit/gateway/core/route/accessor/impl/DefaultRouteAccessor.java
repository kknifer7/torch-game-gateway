package xit.gateway.core.route.accessor.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xit.gateway.core.context.GatewayContext;
import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.core.request.container.GlobalRouteRequestContextContainer;
import xit.gateway.core.request.requester.Requester;
import xit.gateway.core.request.requester.context.impl.DefaultRouteRequestContext;
import xit.gateway.core.route.container.GlobalRoutesContainer;
import xit.gateway.core.route.accessor.RouteAccessor;
import xit.gateway.core.exception.system.SystemException;
import xit.gateway.core.pojo.Route;

import java.util.List;

@Component
public class DefaultRouteAccessor implements RouteAccessor {
    private final GlobalRequesterContainer globalRequesterContainer;
    private final GlobalRoutesContainer globalRoutesContainer;
    private final GlobalRouteRequestContextContainer globalRouteRequestContextContainer;

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
            globalRequesterContainer.put(Requester.get(route));
            globalRouteRequestContextContainer.put(route.getName(), new DefaultRouteRequestContext());
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
