package xit.gateway.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.core.valve.impl.RouteInitializationLoadingValve;

@Component
public class GatewayStarter {
    private final RouteInitializationLoadingValve routeInitializationLoadingValve;

    @Autowired
    public GatewayStarter(RouteInitializationLoadingValve routeInitializationLoadingValve) {
        this.routeInitializationLoadingValve = routeInitializationLoadingValve;
    }

    public void start(){
        routeInitializationLoadingValve.run();
    }
}
