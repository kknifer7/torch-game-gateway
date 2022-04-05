package xit.gateway.context;

import xit.gateway.core.request.container.impl.GlobalRequesterContainer;
import xit.gateway.core.route.container.impl.GlobalRouteContainer;
import xit.gateway.core.route.container.impl.GlobalRouteGroupContainer;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
public interface GatewayContext {
    GlobalRouteContainer routeContainer();
    GlobalRouteGroupContainer routeGroupContainer();
    GlobalRequesterContainer requesterContainer();
}
