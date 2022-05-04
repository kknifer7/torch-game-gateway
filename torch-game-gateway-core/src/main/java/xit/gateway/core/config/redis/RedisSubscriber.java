package xit.gateway.core.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import xit.gateway.api.route.limiter.manager.LimiterManager;
import xit.gateway.api.service.RouteService;
import xit.gateway.constant.RedisChannel;
import xit.gateway.core.filter.LimitingFilter;
import xit.gateway.pojo.Limiter;
import xit.gateway.pojo.Route;
import xit.gateway.utils.RedisUtils;

import java.util.List;

@Component
@DependsOn("redisUtils")  // 必须，否则它会先于redisTemplate加载，构造器中会报空指针异常
public class RedisSubscriber extends MessageListenerAdapter {
    private final StringRedisSerializer stringSerializer;
    private final Jackson2JsonRedisSerializer valueSerializer;
    private final RouteService routeService;
    private final LimiterManager limiterManager;
    private final LimitingFilter limitingFilter;

    @Autowired
    public RedisSubscriber(RouteService routeService, LimiterManager limiterManager, LimitingFilter limitingFilter) {
        this.stringSerializer = RedisUtils.stringSerializer();
        this.valueSerializer = (Jackson2JsonRedisSerializer) RedisUtils.valueSerializer();
        this.routeService = routeService;
        this.limiterManager = limiterManager;
        this.limitingFilter = limitingFilter;
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
            case LIMITER:
                limiterManager.addLimiter((Limiter) object);
                break;
            case LIMITER_SETTINGS_FLUSH:
                limiterManager.flush();
                break;
            case ENABLE_FUSING_ON_LIMITING:
                limitingFilter.flush();
                break;
        }
    }
}
