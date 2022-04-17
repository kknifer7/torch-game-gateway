package xit.gateway.core.route.accessor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.core.request.requester.factory.RequesterFactory;
import xit.gateway.core.route.container.GlobalRouteGroupContainer;
import xit.gateway.core.route.container.RouteGroup;
import xit.gateway.core.route.accessor.RouteAccessor;

import java.util.List;

@Component
public class DefaultRouteAccessor implements RouteAccessor {
    private final GlobalRouteGroupContainer globalRouteGroupContainer;
    private final GlobalRequesterContainer globalRequesterContainer;
    private final RequesterFactory requesterFactory;

    @Autowired
    public DefaultRouteAccessor(GlobalRouteGroupContainer globalRouteGroupContainer, GlobalRequesterContainer globalRequesterContainer, RequesterFactory requesterFactory) {
        this.globalRouteGroupContainer = globalRouteGroupContainer;
        this.globalRequesterContainer = globalRequesterContainer;
        this.requesterFactory = requesterFactory;
    }

    @Override
    public void loadRouteGroups(List<RouteGroup> routeGroupList) {
        globalRouteGroupContainer.putAll(routeGroupList);
        routeGroupList.forEach(rg -> {
            globalRequesterContainer.putAll(requesterFactory.getRequester(rg));
        });
    }
}
