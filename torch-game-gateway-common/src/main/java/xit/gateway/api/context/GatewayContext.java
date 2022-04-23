package xit.gateway.api.context;

import xit.gateway.api.container.request.RequesterContainer;
import xit.gateway.api.container.request.RouteRequestContextContainer;
import xit.gateway.api.container.request.RoutesContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
public interface GatewayContext {
    RequesterContainer requesterContainer();
    RouteRequestContextContainer routeRequestContextContainer();
    RoutesContainer routesContainer();
}
