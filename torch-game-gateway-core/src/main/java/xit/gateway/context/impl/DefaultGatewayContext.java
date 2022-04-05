package xit.gateway.context.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.context.GatewayContext;
import xit.gateway.core.request.container.impl.GlobalRequesterContainer;
import xit.gateway.core.route.container.impl.GlobalRouteContainer;
import xit.gateway.core.route.container.impl.GlobalRouteGroupContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
@Component
public class DefaultGatewayContext implements GatewayContext {
    private final GlobalRouteContainer routeContainer;
    private final GlobalRouteGroupContainer routeGroupContainer;
    private final GlobalRequesterContainer requesterContainer;

    @Autowired
    public DefaultGatewayContext(GlobalRouteContainer routeContainer, GlobalRouteGroupContainer routeGroupContainer, GlobalRequesterContainer requesterContainer){
        this.routeContainer = routeContainer;
        this.routeGroupContainer = routeGroupContainer;
        this.requesterContainer = requesterContainer;
    }

    @Override
    public GlobalRouteContainer routeContainer() {
        return routeContainer;
    }

    @Override
    public GlobalRouteGroupContainer routeGroupContainer() {
        return routeGroupContainer;
    }

    @Override
    public GlobalRequesterContainer requesterContainer() {
        return requesterContainer;
    }
}
