package xit.gateway.core.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import xit.gateway.api.service.RouteService;
import xit.gateway.constant.RedisChannel;
import xit.gateway.pojo.Route;
import xit.gateway.utils.RedisUtils;

import java.util.List;

@Component
@Lazy
public class RedisSubscriber extends MessageListenerAdapter {
    private final StringRedisSerializer stringSerializer;
    private final Jackson2JsonRedisSerializer valueSerializer;
    private final RouteService routeService;

    @Autowired
    public RedisSubscriber(RouteService routeService) {
        this.stringSerializer = RedisUtils.stringSerializer();
        this.valueSerializer = (Jackson2JsonRedisSerializer) RedisUtils.valueSerializer();
        this.routeService = routeService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisChannel channel = RedisChannel.valueOf(stringSerializer.deserialize(message.getChannel()));
        Object object = valueSerializer.deserialize(message.getBody());

        switch (channel){
            case ROUTE:
                routeService.save((Route) object);
                break;
            case ROUTE_LIST:
                routeService.saveList((List<Route>) object);
                break;
            case ROUTE_DELETE:
                routeService.remove((Route) object);
                break;
            case ROUTE_LIST_DELETE:
                routeService.removeByService((String) object);
                break;
        }
    }
}
