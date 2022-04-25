package xit.gateway.core.context.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.api.context.GatewayContext;
import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.request.container.impl.GlobalRequestContextContainer;
import xit.gateway.core.route.container.GlobalRoutesContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
@Component
public class DefaultGatewayContext implements GatewayContext {
    private final GlobalRequestContextContainer routeRequestContextContainer;
    private final GlobalRequesterContainer requesterContainer;
    private final GlobalRoutesContainer serviceRoutesContainer;

    @Autowired
    public DefaultGatewayContext(GlobalRequestContextContainer routeRequestContextContainer, GlobalRequesterContainer requesterContainer, GlobalRoutesContainer serviceRoutesContainer){
        this.routeRequestContextContainer = routeRequestContextContainer;
        this.requesterContainer = requesterContainer;
        this.serviceRoutesContainer = serviceRoutesContainer;
    }

    @Override
    public GlobalRequesterContainer requesterContainer() {
        return requesterContainer;
    }

    @Override
    public GlobalRequestContextContainer routeRequestContextContainer() {
        return routeRequestContextContainer;
    }

    @Override
    public GlobalRoutesContainer routesContainer() {
        return serviceRoutesContainer;
    }
}
