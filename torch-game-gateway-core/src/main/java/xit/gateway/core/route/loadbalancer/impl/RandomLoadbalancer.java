package xit.gateway.core.route.loadbalancer.impl;

import org.apache.commons.lang3.RandomUtils;
import xit.gateway.api.route.loadbalancer.Loadbalancer;
import xit.gateway.api.request.context.RouteRequestContext;
import xit.gateway.pojo.Route;

import java.util.List;

/**
 * @author Knifer
 * Description: 负载均衡器。
 * Date: 2022/04/10
 */
public class RandomLoadbalancer implements Loadbalancer {
    @Override
    public Route choose(List<Route> routes, RouteRequestContext requesterContext) {
        int size = routes.size();

        // TODO 处理一下null值情况
        return size == 0 ? null : routes.get(RandomUtils.nextInt(0, size));
    }
}
