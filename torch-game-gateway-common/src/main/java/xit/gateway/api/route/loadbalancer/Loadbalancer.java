package xit.gateway.api.route.loadbalancer;

import xit.gateway.api.request.context.RouteRequestContext;
import xit.gateway.pojo.Route;

import java.util.List;

/**
 * @author Knifer
 * Description: 负载均衡器
 * Date: 2022/04/10
 */
public interface Loadbalancer {
    Route choose(List<Route> routes, RouteRequestContext requesterContext);
}
