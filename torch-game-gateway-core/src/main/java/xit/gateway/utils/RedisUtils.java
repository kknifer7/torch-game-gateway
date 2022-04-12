package xit.gateway.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtils for Torch Game by Knifer
 */
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class RedisUtils {
    private static RedisTemplate redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate redisTemplate) {
        if(RedisUtils.redisTemplate != null){
            throw new RuntimeException("别手动实例化工具类！");
        }
        RedisUtils.redisTemplate = redisTemplate;
    }

    public static<T> void set(String key, T value){
        redisTemplate.opsForValue().set(key, value);
    }

    public static<T> void set(String key, T value, long timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public static<T> T get(String key, Class<T> valueClazz){
        return (T) redisTemplate.opsForValue().get(key);
    }

    public static boolean isExist(String key){
        return redisTemplate.hasKey(key);
    }

    public static<T> void pfSet(String key, T value){
        redisTemplate.opsForHyperLogLog().add(key, value);
    }

    public static Long pfGet(String key){
        return redisTemplate.opsForHyperLogLog().size(key);
    }

    public static<T> void hSet(String key, String hashKey, T value){
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public static<T> T hGet(String key, String hashKey, Class<T> valueClazz){
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    public static void hIncrBy(String key, String hashKey, double incrByNumber){
        redisTemplate.opsForHash().increment(key, hashKey, incrByNumber);
    }

    public static<T> Map<String, T> hGetAll(String key, Class<T> hashValueClazz){
        return redisTemplate.opsForHash().entries(key);
    }

    public static<T> Boolean delete(String key){
        return redisTemplate.delete(key);
    }
}