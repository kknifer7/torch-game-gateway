package xit.gateway.core.limiter.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xit.gateway.api.route.limiter.manager.LimiterManager;
import xit.gateway.api.service.ConfigService;
import xit.gateway.core.limiter.container.impl.GlobalLimiterContainer;
import xit.gateway.core.limiter.impl.DefaultLimiter;
import xit.gateway.pojo.Limiter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class DefaultLimiterManager implements LimiterManager {
    private final GlobalLimiterContainer limiterContainer;
    private final ConfigService configService;
    private long limit;
    private long limitingTimeout;
    private TimeUnit limitingTimeUnit;

    @Autowired
    public DefaultLimiterManager(GlobalLimiterContainer limiterContainer, ConfigService configService) {
        this.limiterContainer = limiterContainer;
        this.configService = configService;
        flush();
    }

    public void flush(){
        Mono.zip(
                        configService.get("limiting_threshold"),
                        configService.get("limiting_time_unit"),
                        configService.get("limiting_timeout")
                )
                .flatMap(tuple3 -> {
                    String limitStr = tuple3.getT1();
                    int limitParse;
                    String limitTimeoutStr = tuple3.getT3();

                    limit = StringUtils.isBlank(limitStr) ?
                            10 :
                            ((limitParse = Integer.parseInt(limitStr)) > 0 ?
                                    limitParse :
                                    10
                            );
                    limitingTimeout = StringUtils.isBlank(limitTimeoutStr) ? 10 : Long.parseLong(limitTimeoutStr);
                    limitingTimeUnit = Optional.ofNullable(TimeUnit.valueOf(tuple3.getT2())).orElse(TimeUnit.SECONDS);
                    limiterContainer.getAll().forEach(lt -> {
                        DefaultLimiter limiter = (DefaultLimiter) lt;

                        limiter.setLimitingTimeUnit(limitingTimeUnit);
                        limiter.setLimitingTimeout(limitingTimeout);
                        limiter.setLimit(limit);
                    });

                    return Mono.empty();
                }).block();
    }

    @Override
    public void addLimiter(Serializable id) {
        addLimiter(id, limit, limitingTimeout, limitingTimeUnit);
    }

    @Override
    public void addLimiter(Serializable userId, Long limit, Long limitingTimeout, TimeUnit limitingTimeUnit) {
        limiterContainer.put(new DefaultLimiter(userId, limit, limitingTimeout, limitingTimeUnit));
    }

    @Override
    public List<Limiter> getAllLimiterVO() {
        return limiterContainer.getAll().stream().map(limiter -> {
            Limiter limiterVO = new Limiter();

            BeanUtils.copyProperties(limiter, limiterVO);

            return limiterVO;
        }).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void removeLimiter(Serializable id) {
        limiterContainer.remove(id);
    }
}
