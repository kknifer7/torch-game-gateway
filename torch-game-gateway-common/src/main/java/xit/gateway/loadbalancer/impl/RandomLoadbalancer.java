package xit.gateway.loadbalancer.impl;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import xit.gateway.api.loadbalancer.Loadbalanceable;
import xit.gateway.api.request.context.RequestContext;
import xit.gateway.api.route.loadbalancer.Loadbalancer;

import java.util.Collection;

/**
 * @author Knifer
 * Description: 负载均衡器。
 * Date: 2022/04/10
 */
public class RandomLoadbalancer implements Loadbalancer {
    @Override
    public Loadbalanceable choose(Collection<? extends Loadbalanceable> routes, RequestContext requesterContext) {
        int size = routes.size();

        return size == 0 ? null : Lists.newArrayList(routes).get(RandomUtils.nextInt(0, size));
    }
}
