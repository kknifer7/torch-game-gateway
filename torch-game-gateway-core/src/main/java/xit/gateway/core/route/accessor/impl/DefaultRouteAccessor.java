package xit.gateway.core.route.accessor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.core.request.container.impl.GlobalRequesterContainer;
import xit.gateway.core.request.requester.factory.RequesterFactory;
import xit.gateway.core.route.container.impl.GlobalRouteContainer;
import xit.gateway.core.route.container.impl.GlobalRouteGroupContainer;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.core.route.accessor.RouteAccessor;

import java.util.List;

@Component
public class DefaultRouteAccessor implements RouteAccessor {
    private final GlobalRouteContainer globalRouteContainer;
    private final GlobalRouteGroupContainer globalRouteGroupContainer;
    private final GlobalRequesterContainer globalRequesterContainer;
    private final RequesterFactory requesterFactory;

    @Autowired
    public DefaultRouteAccessor(GlobalRouteContainer globalRouteContainer, GlobalRouteGroupContainer globalRouteGroupContainer, GlobalRequesterContainer globalRequesterContainer, RequesterFactory requesterFactory) {
        this.globalRouteContainer = globalRouteContainer;
        this.globalRouteGroupContainer = globalRouteGroupContainer;
        this.globalRequesterContainer = globalRequesterContainer;
        this.requesterFactory = requesterFactory;
    }

    @Override
    public void loadRouteGroups(List<RouteGroup> routeGroupList) {
        globalRouteGroupContainer.putAll(routeGroupList);
        routeGroupList.forEach(rg -> {
            globalRouteContainer.putAll(rg);
            globalRequesterContainer.putAll(requesterFactory.getRequester(rg));
        });
    }
}
