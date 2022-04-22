package xit.gateway.core.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.core.service.impl.GatewayUserService;

@SpringBootTest
public class TestGatewayUserService {
    @Autowired
    private GatewayUserService gatewayUserService;

    @Test
    void test(){
        System.out.println(gatewayUserService.findByUsername("test").block());
    }
}
