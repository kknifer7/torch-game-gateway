package xit.gateway.core.limiter.impl;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xit.gateway.api.route.limiter.Limiter;
import xit.gateway.api.service.ConfigService;

import java.util.concurrent.TimeUnit;

@Component
@SuppressWarnings("all")
public class DefaultLimiter implements Limiter {
    private final ConfigService configService;
    private long limitingTimeout;
    private TimeUnit limitingTimeUnit;
    private RateLimiter rateLimiter;

    @Autowired
    public DefaultLimiter(ConfigService configService) {
        this.configService = configService;
        this.rateLimiter = RateLimiter.create(10);
        flush();
    }

    @Override
    public void flush(){
        Mono.zip(
                    configService.get("limiting_threshold"),
                    configService.get("limiting_time_unit"),
                    configService.get("limiting_timeout")
                )
                .flatMap(tuple3 -> {
                    String limitStr = tuple3.getT1();
                    long limit;
                    String limitTimeoutStr = tuple3.getT3();

                    limit = StringUtils.isBlank(limitStr) ? 10 : Integer.parseInt(limitStr);
                    this.limitingTimeUnit = TimeUnit.valueOf(tuple3.getT2());
                    this.limitingTimeout = StringUtils.isBlank(limitTimeoutStr) ? 10 : Long.parseLong(limitTimeoutStr);
                    this.rateLimiter.setRate(limit);

                    return Mono.empty();
                }).subscribe();
    }

    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire(limitingTimeout, limitingTimeUnit);
    }
}
