package xit.gateway.core.route.loadbalancer.impl;

import xit.gateway.core.route.loadbalancer.Loadbalancer;
import xit.gateway.core.request.requester.context.RouteRequestContext;
import xit.gateway.core.pojo.Route;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Knifer
 * Description: 轮询负载均衡器
 * Date: 2022/04/10
 */
public class RoundRobinLoadbalancer implements Loadbalancer {

    @Override
    public Route choose(List<Route> routes, RouteRequestContext requesterContext) {
        int size = routes.size();
        AtomicInteger lastIdx = requesterContext.lastCalledIndex();
        int curr,next;

        if (size == 0) return null;

        while (true){
            curr = lastIdx.get();
            next = (curr + 1) % size;
            if (lastIdx.compareAndSet(curr, next)) {
                return routes.get(next);
            }
        }
    }
}
