package xit.gateway.context;

import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.core.route.container.GlobalRouteGroupContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
public interface GatewayContext {
    GlobalRouteGroupContainer routeGroupContainer();
    GlobalRequesterContainer requesterContainer();
}
