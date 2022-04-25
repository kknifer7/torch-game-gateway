package xit.gateway.loadbalancer.impl;

import xit.gateway.api.loadbalancer.Loadbalanceable;
import xit.gateway.api.request.context.RequestContext;
import xit.gateway.api.route.loadbalancer.Loadbalancer;

import java.util.Collection;

/**
 * @author Knifer
 * Description: 如果不需要进行负载均衡，使用这个
 * Date: 2022/04/13
 */
public class NoLoadbalancer implements Loadbalancer {
    @Override
    public Loadbalanceable choose(Collection<? extends Loadbalanceable> lists, RequestContext requesterContext) {
        return lists.size() == 0 ? null : lists.iterator().next();
    }
}
