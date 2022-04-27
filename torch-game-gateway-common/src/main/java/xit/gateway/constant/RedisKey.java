package xit.gateway.constant;

import java.util.concurrent.TimeUnit;

public enum RedisKey implements ValueEnum<String>{
    // normal（fuse_count:routeId），为了能灵活配置，这个过期时间让deacon的熔断器去数据库中获取
    FUSE_COUNT("fuse_count:", -1, null),
    // keys
    ROUTE("route:", -1, null);

    private final String value;
    // 过期时间，-1表示永不过期
    private final long lifetime;
    private final TimeUnit timeUnit;

    RedisKey(String value, long lifetime, TimeUnit timeUnit) {
        this.value = value;
        this.lifetime = lifetime;
        this.timeUnit = timeUnit;
    }

    public long getLifetime() {
        return lifetime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String extend(String suffix){
        return value + suffix;
    }
}
