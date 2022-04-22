package xit.gateway.core.valve;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.core.context.impl.DefaultGatewayContext;
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
        Assertions.assertNotEquals(null, gatewayContext.routesContainer().get("service-01"));
    }
}
