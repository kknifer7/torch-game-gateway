package xit.gateway.core.loadbalancer.impl;

import xit.gateway.core.loadbalancer.Loadbalancer;
import xit.gateway.core.request.requester.context.RequesterContext;
import xit.gateway.pojo.Route;

import java.util.List;

/**
 * @author Knifer
 * Description: 如果不需要进行负载均衡，使用这个
 * Date: 2022/04/13
 */
public class NoLoadbalancer implements Loadbalancer {
    @Override
    public Route choose(List<Route> routes, RequesterContext requesterContext) {
        return routes.size() == 0 ? null : routes.get(0);
    }
}
