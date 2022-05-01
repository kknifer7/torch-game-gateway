package xit.gateway.core.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.constant.ProtocolType;
import xit.gateway.constant.RedisChannel;
import xit.gateway.pojo.Route;
import xit.gateway.utils.RedisUtils;

@SpringBootTest
public class TestRedisUtils {
    @Test
    void test(){
        RedisUtils.publish(RedisChannel.ROUTE, new Route("123", "123", "123", ProtocolType.HTTP, "127.0.0.1", 4040, "http://www.aaa.com/", true, null, null, null));
    }
}
