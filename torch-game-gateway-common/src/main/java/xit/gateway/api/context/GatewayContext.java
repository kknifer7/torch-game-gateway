package xit.gateway.api.context;

import xit.gateway.api.request.container.RequesterContainer;
import xit.gateway.api.request.container.RequestContextContainer;
import xit.gateway.api.request.container.RoutesContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
public interface GatewayContext {
    RequesterContainer requesterContainer();
    RequestContextContainer routeRequestContextContainer();
    RoutesContainer routesContainer();
}
