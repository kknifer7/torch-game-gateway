package xit.gateway.loadbalancer.impl;

import com.google.common.collect.Lists;
import xit.gateway.api.loadbalancer.Loadbalanceable;
import xit.gateway.api.request.context.RequestContext;
import xit.gateway.api.route.loadbalancer.Loadbalancer;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Knifer
 * Description: 轮询负载均衡器
 * Date: 2022/04/10
 */
public class RoundRobinLoadbalancer implements Loadbalancer {

    @Override
    public Loadbalanceable choose(Collection<? extends Loadbalanceable> list, RequestContext requesterContext) {
        int size = list.size();
        AtomicInteger lastIdx = requesterContext.lastCalledIndex();
        int curr,next;

        if (size == 0) return null;

        while (true){
            curr = lastIdx.get();
            next = (curr + 1) % size;
            if (lastIdx.compareAndSet(curr, next)) {
                return Lists.newArrayList(list).get(next);
            }
        }
    }
}
