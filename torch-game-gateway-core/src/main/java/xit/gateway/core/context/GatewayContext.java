package xit.gateway.core.context;

import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.core.request.container.GlobalRouteRequestContextContainer;
import xit.gateway.core.route.container.GlobalRoutesContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
public interface GatewayContext {
    GlobalRequesterContainer requesterContainer();
    GlobalRouteRequestContextContainer routeRequestContextContainer();
    GlobalRoutesContainer routesContainer();
}
