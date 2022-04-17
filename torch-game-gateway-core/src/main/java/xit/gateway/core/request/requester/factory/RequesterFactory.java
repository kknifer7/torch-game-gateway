package xit.gateway.core.request.requester.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.core.loadbalancer.Loadbalancer;
import xit.gateway.core.request.requester.Requester;
import xit.gateway.core.request.requester.impl.DefaultHttpRequester;
import xit.gateway.core.request.requester.impl.DefaultRpcRequester;
import xit.gateway.core.route.container.RouteGroup;

@Component
public class RequesterFactory {
    private final Loadbalancer loadbalancer;

    @Autowired
    public RequesterFactory(Loadbalancer loadbalancer) {
        this.loadbalancer = loadbalancer;
    }

    public Requester[] getRequester(RouteGroup routeGroup){
        return new Requester[]{
                new DefaultHttpRequester(routeGroup, loadbalancer),
                new DefaultRpcRequester(routeGroup, loadbalancer)
        };
    }
}
