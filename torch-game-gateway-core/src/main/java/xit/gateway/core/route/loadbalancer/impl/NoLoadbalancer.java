package xit.gateway.core.route.loadbalancer.impl;

import xit.gateway.api.route.loadbalancer.Loadbalancer;
import xit.gateway.api.request.context.RouteRequestContext;
import xit.gateway.pojo.Route;

import java.util.List;

/**
 * @author Knifer
 * Description: 如果不需要进行负载均衡，使用这个
 * Date: 2022/04/13
 */
public class NoLoadbalancer implements Loadbalancer {
    @Override
    public Route choose(List<Route> routes, RouteRequestContext requesterContext) {
        return routes.size() == 0 ? null : routes.get(0);
    }
}
