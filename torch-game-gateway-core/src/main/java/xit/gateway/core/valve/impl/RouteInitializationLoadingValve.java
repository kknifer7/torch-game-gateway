package xit.gateway.core.valve.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xit.gateway.context.impl.DefaultGatewayContext;
import xit.gateway.core.request.container.impl.GlobalRequesterContainer;
import xit.gateway.core.request.requester.factory.RequesterFactory;
import xit.gateway.core.route.container.impl.GlobalRouteContainer;
import xit.gateway.core.route.container.impl.GlobalRouteGroupContainer;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.core.route.reader.RouteReader;
import xit.gateway.core.route.reader.impl.DefaultRouteReader;
import xit.gateway.exception.route.RouteLoadingException;

import java.io.IOException;
import java.util.List;

/**
 * @author knifer
 * Description: 路由加载阀门
 * Date: 2022/03/28
 */
@Component
public class RouteInitializationLoadingValve extends ProcessCoreValve{
    private final RouteReader routeReader;

    private final GlobalRouteContainer globalRouteContainer;

    private final GlobalRouteGroupContainer globalRouteGroupContainer;

    private final GlobalRequesterContainer globalRequesterContainer;

    private final RequesterFactory requesterFactory;

    @Value("${torch.gateway.init.routes-json-path}")
    private String initRoutesJsonPath;

    @Autowired
    public RouteInitializationLoadingValve(DefaultGatewayContext gatewayContext, RequesterFactory requesterFactory) {
        this.routeReader = new DefaultRouteReader();
        this.globalRouteContainer = gatewayContext.routeContainer();
        this.globalRouteGroupContainer = gatewayContext.routeGroupContainer();
        this.globalRequesterContainer = gatewayContext.requesterContainer();
        this.requesterFactory = requesterFactory;
    }

    @Override
    protected void work() {
        List<RouteGroup> routeGroups;

        try {
            routeGroups = routeReader.loadRouteGroupFromJSON(initRoutesJsonPath);
        } catch (IOException e) {
            throw new RouteLoadingException("routeing load failed: " + e.getMessage());
        }

        if (routeGroups != null && !routeGroups.isEmpty()){
            globalRouteGroupContainer.putAll(routeGroups);
            routeGroups.forEach(rg -> {
                globalRouteContainer.putAll(rg);
                globalRequesterContainer.putAll(requesterFactory.getRequester(rg));
            });
        }
    }
}
