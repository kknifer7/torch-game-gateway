package xit.gateway.core.route.accessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.core.request.container.impl.GlobalRequesterContainer;
import xit.gateway.core.request.requester.factory.RequesterFactory;
import xit.gateway.core.route.container.impl.GlobalRouteContainer;
import xit.gateway.core.route.container.impl.GlobalRouteGroupContainer;
import xit.gateway.core.route.container.impl.RouteGroup;

import java.util.List;

@Component
public abstract class AbstractRouteAccessor implements RouteAccessor {
    protected final GlobalRouteContainer globalRouteContainer;
    protected final GlobalRouteGroupContainer globalRouteGroupContainer;
    protected final GlobalRequesterContainer globalRequesterContainer;
    protected final RequesterFactory requesterFactory;

    @Autowired
    public AbstractRouteAccessor(GlobalRouteContainer globalRouteContainer, GlobalRouteGroupContainer globalRouteGroupContainer, GlobalRequesterContainer globalRequesterContainer, RequesterFactory requesterFactory) {
        this.globalRouteContainer = globalRouteContainer;
        this.globalRouteGroupContainer = globalRouteGroupContainer;
        this.globalRequesterContainer = globalRequesterContainer;
        this.requesterFactory = requesterFactory;
    }

    @Override
    public void mountRouteGroups(List<RouteGroup> routeGroupList) {
        globalRouteGroupContainer.putAll(routeGroupList);
        routeGroupList.forEach(rg -> {
            globalRouteContainer.putAll(rg);
            globalRequesterContainer.putAll(requesterFactory.getRequester(rg));
        });
    }
}
