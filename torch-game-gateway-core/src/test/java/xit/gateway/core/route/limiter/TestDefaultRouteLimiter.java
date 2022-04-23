package xit.gateway.core.route.limiter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.core.route.limiter.impl.DefaultRouteLimiter;
import xit.gateway.pojo.Route;

@SpringBootTest
public class TestDefaultRouteLimiter {
    @Test
    void test(){
        new DefaultRouteLimiter(new Route());
    }
}
