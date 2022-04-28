package xit.gateway.core.route.accessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.constant.ProtocolType;
import xit.gateway.pojo.Route;
import xit.gateway.utils.RedisUtils;
import xit.gateway.utils.UUIDUtils;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
public class TestRouteRedisLoader {
    @Autowired
    private RouteAccessor accessor;

    @Test
    void testRedisUsability(){
        RedisUtils.set("torch_game_gateway_test", "test data");
        Assertions.assertEquals("test data", RedisUtils.get("torch_game_gateway_test", String.class));
        Assertions.assertEquals(true, RedisUtils.delete("torch_game_gateway_test"));
    }

    @Test
    void testAccessor(){

    }
}
