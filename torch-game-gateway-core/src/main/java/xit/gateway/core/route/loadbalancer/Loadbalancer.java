package xit.gateway.core.route.loadbalancer;

import xit.gateway.core.request.requester.context.RequesterContext;
import xit.gateway.pojo.Route;

import java.util.List;

/**
 * @author Knifer
 * Description: 负载均衡器
 * Date: 2022/04/10
 */
public interface Loadbalancer {
    Route choose(List<Route> routes, RequesterContext requesterContext);
}
