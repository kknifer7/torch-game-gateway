package xit.gateway.core.request.requester.factory;

import org.springframework.stereotype.Component;
import xit.gateway.core.request.requester.Requester;
import xit.gateway.core.request.requester.impl.DefaultHttpRequester;
import xit.gateway.core.request.requester.impl.DefaultRpcRequester;
import xit.gateway.core.route.container.impl.RouteGroup;

@Component
public class RequesterFactory {
    public Requester[] getRequester(RouteGroup routeGroup){
        return new Requester[]{
                new DefaultHttpRequester(routeGroup),
                new DefaultRpcRequester(routeGroup)
        };
    }
}
