package xit.gateway.core.config.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import xit.gateway.utils.RedisUtils;

@Component
public class RedisSubscriber extends MessageListenerAdapter {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer redisSerializer = RedisUtils.stringSerializer();

        System.out.println(redisSerializer.deserialize(message.getChannel()));
        System.out.println("信息为");
        System.out.println(redisSerializer.deserialize(message.getBody()));
    }
}
