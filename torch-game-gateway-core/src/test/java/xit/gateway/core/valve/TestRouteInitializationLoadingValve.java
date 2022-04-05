package xit.gateway.core.valve;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.context.impl.DefaultGatewayContext;
import xit.gateway.core.valve.impl.RouteInitializationLoadingValve;

@SpringBootTest
public class TestRouteInitializationLoadingValve {
    @Autowired
    private RouteInitializationLoadingValve valve;
    @Autowired
    private DefaultGatewayContext gatewayContext;

    @Test
    void test(){
        valve.run();
        Assertions.assertNotEquals(null, gatewayContext.routeContainer().get("service-01"));
        System.out.println(gatewayContext.routeContainer().get("service-01").get(0));
    }
}
