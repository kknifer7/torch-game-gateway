package xit.gateway.core.fuse.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xit.gateway.constant.RedisKey;
import xit.gateway.core.fuse.Fuse;
import xit.gateway.pojo.CallRecord;
import xit.gateway.utils.RedisUtils;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DefaultFuse implements Fuse {
    private final AtomicInteger FUSING_THRESHOLD;
    private final Logger logger = LoggerFactory.getLogger(DefaultFuse.class);

    public DefaultFuse(){
        this.FUSING_THRESHOLD = new AtomicInteger(-1);
        flush();
    }

    @Override
    public void fuseIfNecessary(CallRecord record) {
        if (record.getSuccess()){
            return;
        }

        String routeId = record.getRoute().getId();
        Integer fuseCount;

        fuseCount = RedisUtils.hGet(RedisKey.FUSE_COUNT, routeId, Integer.class);
        if (fuseCount >= FUSING_THRESHOLD.get()){
            // TODO 下达熔断通知
            logger.info("熔断路由：" + record.getRoute().getDesc());
        }
    }

    @Override
    public void flush() {
        // TODO Integer fusingThreshold = RedisUtils.get(RedisKey.FUSING_THRESHOLD, Integer.class);
        Integer fusingThreshold = 5;

        while (true){
            if (FUSING_THRESHOLD.compareAndSet(FUSING_THRESHOLD.get(), fusingThreshold)){
                break;
            }
        }
    }
}
