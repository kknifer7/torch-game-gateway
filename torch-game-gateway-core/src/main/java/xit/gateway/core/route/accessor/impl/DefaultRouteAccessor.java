package xit.gateway.core.route.accessor.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xit.gateway.api.request.container.RequesterContainer;
import xit.gateway.api.request.container.RequestContextContainer;
import xit.gateway.api.request.container.RoutesContainer;
import xit.gateway.api.context.GatewayContext;
import xit.gateway.api.route.limiter.manager.LimiterManager;
import xit.gateway.request.context.impl.DefaultRequestContext;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.core.request.requester.factory.RequesterFactory;
import xit.gateway.exception.system.SystemException;
import xit.gateway.pojo.Route;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultRouteAccessor implements RouteAccessor {
    private final RequesterContainer globalRequesterContainer;
    private final RoutesContainer globalRoutesContainer;
    private final RequestContextContainer globalRouteRequestContextContainer;
    private final LimiterManager limiterManager;

    @Autowired
    public DefaultRouteAccessor(GatewayContext context) {
        this.globalRequesterContainer = context.requesterContainer();
        this.globalRoutesContainer = context.routesContainer();
        this.globalRouteRequestContextContainer = context.routeRequestContextContainer();
        this.limiterManager = context.limiterManager();
    }

    @Override
    public void loadRoute(Route route) {
        globalRoutesContainer.put(route);
        globalRequesterContainer.put(RequesterFactory.get(route));
        globalRouteRequestContextContainer.put(route.getServiceName(), new DefaultRequestContext());
        limiterManager.addLimiter(route.getId());
    }

    @Override
    public void loadRoutes(Collection<Route> routeList) {
        routeList.forEach(route -> {
            globalRoutesContainer.put(route);
            globalRequesterContainer.put(RequesterFactory.get(route));
            globalRouteRequestContextContainer.put(route.getServiceName(), new DefaultRequestContext());
            limiterManager.addLimiter(route.getId());
        });
    }

    @Override
    public void updateRoute(Route route) {
        List<Route> routes = globalRoutesContainer.get(route.getServiceName());
        Route oldRoute;

        if (CollectionUtils.isEmpty(routes)){
            // 路由所属的服务不存在
            throw new SystemException("route need update's service doesn't exist");
        }
        oldRoute = routes.stream()
                .filter(r -> !StringUtils.equals(r.getId(), route.getId()))
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
            oldRoute.setStatus(false);
        }
    }

    @Override
    public void removeRoute(String serviceId, String routeId) {
        Optional.ofNullable(globalRoutesContainer.get(serviceId))
                        .ifPresent(routes ->
                                routes.removeIf(route ->
                                        StringUtils.equals(route.getId(), routeId)));

        // TODO 移除单个路由时，需要请求器对象中有路由id才能查找到目标请求器并加以删除（请求上下文也是差不多）
        // globalRouteRequestContextContainer.remove(...)
        // ...RequestContextContainer.remove(...)
        limiterManager.removeLimiter(routeId);
    }

    @Override
    public void removeAllRoute(String serviceId) {
        globalRoutesContainer.remove(serviceId)
                        .forEach(route -> limiterManager.removeLimiter(route.getId()));
        globalRequesterContainer.remove(serviceId);
        globalRouteRequestContextContainer.remove(serviceId);
    }
}
