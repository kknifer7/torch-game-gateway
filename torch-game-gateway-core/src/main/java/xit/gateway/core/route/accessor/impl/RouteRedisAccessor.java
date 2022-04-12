package xit.gateway.core.route.accessor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.core.request.container.impl.GlobalRequesterContainer;
import xit.gateway.core.request.requester.factory.RequesterFactory;
import xit.gateway.core.route.container.impl.GlobalRouteContainer;
import xit.gateway.core.route.container.impl.GlobalRouteGroupContainer;
import xit.gateway.core.route.accessor.AbstractRouteAccessor;

/**
 * @author Knifer
 * Description: 路由信息读取器。用于从Redis读取路由信息
 * Date: 2022/04/12
 */
@Component
public class RouteRedisAccessor extends AbstractRouteAccessor {
    @Autowired
    public RouteRedisAccessor(GlobalRouteContainer globalRouteContainer, GlobalRouteGroupContainer globalRouteGroupContainer, GlobalRequesterContainer globalRequesterContainer, RequesterFactory requesterFactory) {
        super(globalRouteContainer, globalRouteGroupContainer, globalRequesterContainer, requesterFactory);
    }

    /**
     * TODO 从Redis加载所有路由组并完成挂载
     */
    public void loadRouteGroups(){

    }
}
