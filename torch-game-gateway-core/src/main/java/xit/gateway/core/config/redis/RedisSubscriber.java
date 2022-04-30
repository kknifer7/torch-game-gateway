package xit.gateway.core.config.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber extends MessageListenerAdapter {
    @Override
    public void onMessage(Message message, byte[] pattern) {
    }
}
