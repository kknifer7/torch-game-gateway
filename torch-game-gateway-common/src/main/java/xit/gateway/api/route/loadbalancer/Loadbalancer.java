package xit.gateway.api.route.loadbalancer;

import xit.gateway.api.loadbalancer.Loadbalanceable;
import xit.gateway.api.request.context.RequestContext;

import java.util.Collection;

/**
 * @author Knifer
 * Description: 负载均衡器
 * Date: 2022/04/10
 */
public interface Loadbalancer {
    Loadbalanceable choose(Collection<? extends Loadbalanceable> list, RequestContext requesterContext);
}
