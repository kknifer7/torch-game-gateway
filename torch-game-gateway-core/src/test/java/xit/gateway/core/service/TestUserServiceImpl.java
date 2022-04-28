package xit.gateway.core.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.core.service.impl.UserServiceImpl;

@SpringBootTest
public class TestUserServiceImpl {
    @Autowired
    private UserServiceImpl gatewayUserService;

    @Test
    void test(){
        System.out.println(gatewayUserService.findUserWithAuthsByUsername("zeyu12").block());
    }
}
