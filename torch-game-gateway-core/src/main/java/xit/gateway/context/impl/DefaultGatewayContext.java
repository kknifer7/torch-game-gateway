package xit.gateway.context.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.context.GatewayContext;
import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.core.route.container.GlobalRouteGroupContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
@Component
public class DefaultGatewayContext implements GatewayContext {
    private final GlobalRouteGroupContainer routeGroupContainer;
    private final GlobalRequesterContainer requesterContainer;

    @Autowired
    public DefaultGatewayContext(GlobalRouteGroupContainer routeGroupContainer, GlobalRequesterContainer requesterContainer){
        this.routeGroupContainer = routeGroupContainer;
        this.requesterContainer = requesterContainer;
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
