package xit.gateway.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import xit.gateway.constant.RedisKey;

import java.util.Map;
import java.util.Set;
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

    public static<T> void set(String key, T value, long lifeTime, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, lifeTime, timeUnit);
    }

    public static<T> void set(RedisKey key, T value, long lifeTime, TimeUnit timeUnit){
        set(key.getValue(), value, lifeTime, timeUnit);
    }

    public static<T> void set(RedisKey key, T value){
        set(key.getValue(), value, key.getLifetime(), key.getTimeUnit());
    }

    public static<T> T get(String key, Class<T> valueClazz){
        return (T) redisTemplate.opsForValue().get(key);
    }

    public static<T> T get(RedisKey key, Class<T> valueClazz){
        return get(key.getValue(), valueClazz);
    }

    public static<T> Set<T> keys(String key, Class<T> valueClazz){
        return redisTemplate.opsForValue().getOperations().keys(key);
    }

    public static void publish(String channel, Object obj){
        redisTemplate.convertAndSend(channel, obj);
    }

    public static RedisSerializer stringSerializer(){
        return redisTemplate.getStringSerializer();
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

    public static<T> void hSet(RedisKey key, String hashKey, T value){
        hSet(key.getValue(), hashKey, value);
    }

    public static<T> T hGet(String key, String hashKey, Class<T> valueClazz){
        Object result = redisTemplate.opsForHash().get(key, hashKey);

        return result == null ? null : (T) result;
    }

    public static<T> T hGet(RedisKey key, String hashKey, Class<T> valueClazz){
        return hGet(key.getValue(), hashKey, valueClazz);
    }

    public static void hIncrBy(String key, String hashKey, double incrByNumber){
        redisTemplate.opsForHash().increment(key, hashKey, incrByNumber);
    }

    public static void hIncrBy(RedisKey key, String hashKey, double incrByNumber){
        hIncrBy(key.getValue(), hashKey, incrByNumber);
    }

    public static<T> void lPush(String key, T value){
        redisTemplate.opsForList().leftPush(key, value);
    }

    public static<T> void lPush(RedisKey key, T value){
        lPush(key.getValue(), value);
    }

    public static<T> Map<String, T> hGetAll(String key, Class<T> hashValueClazz){
        return redisTemplate.opsForHash().entries(key);
    }

    public static<T> Boolean delete(String key){
        return redisTemplate.delete(key);
    }
}