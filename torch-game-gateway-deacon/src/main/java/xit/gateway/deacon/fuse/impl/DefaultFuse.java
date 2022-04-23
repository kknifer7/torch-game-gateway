package xit.gateway.deacon.fuse.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.constant.RedisKey;
import xit.gateway.api.fuse.Fuse;
import xit.gateway.api.service.ConfigService;
import xit.gateway.pojo.CallRecord;
import xit.gateway.utils.RedisUtils;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DefaultFuse implements Fuse {
    private final AtomicInteger FUSING_THRESHOLD;
    private final ConfigService configService;
    private final Logger logger = LoggerFactory.getLogger(DefaultFuse.class);

    @Autowired
    public DefaultFuse(ConfigService configService){
        this.configService = configService;
        this.FUSING_THRESHOLD = new AtomicInteger(-1);
        flush();
    }

    @Override
    public void fuseIfNecessary(CallRecord record) {
        if (record.getSuccess()){
            return;
        }

        String routeId = record.getRouteId();
        Integer fuseCount;

        fuseCount = RedisUtils.hGet(RedisKey.FUSE_COUNT, routeId, Integer.class);
        fuseCount = fuseCount == null ? 1 : fuseCount + 1;
        if (fuseCount >= FUSING_THRESHOLD.get()){
            // TODO 下达熔断通知
            logger.info("熔断路由：" + record.getRouteDesc());
        }
        RedisUtils.hSet(RedisKey.FUSE_COUNT, routeId, fuseCount);
    }

    @Override
    public void flush() {
        String fusingThresholdStr = configService.get("fusing_threshold").block();
        int fusingThreshold = StringUtils.isBlank(fusingThresholdStr) ? 5 : Integer.parseInt(fusingThresholdStr);

        while (true){
            if (FUSING_THRESHOLD.compareAndSet(FUSING_THRESHOLD.get(), fusingThreshold)){
                break;
            }
        }
    }
}
