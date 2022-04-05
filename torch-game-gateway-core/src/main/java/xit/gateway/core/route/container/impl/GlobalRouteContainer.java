package xit.gateway.core.route.container.impl;

import org.springframework.stereotype.Component;
import xit.gateway.pojo.Route;

import java.util.List;
import java.util.Map;

/**
 * @author Knifer
 * Description: 路由容器
 * Date: 2022/03/27
 */
@Component
public class GlobalRouteContainer extends AbstractRouteContainer{
    public GlobalRouteContainer(){
        super();
    }

    @Override
    public boolean contains(String primaryKey) {
        return routeMap.containsKey(primaryKey);
    }

    @Override
    public boolean isEmpty() {
        return routeMap.isEmpty();
    }

    public void putAll(RouteGroup routeGroup){
        Map<String, List<Route>> httpRoutes = routeGroup.getHttpRoutes();
        Map<String, List<Route>> rpcRoutes = routeGroup.getRpcRoutes();

        if (httpRoutes != null){
            routeMap.putAll(httpRoutes);
        }
        if (rpcRoutes != null){
            routeMap.putAll(rpcRoutes);
        }
    }
}
