package xit.gateway.deacon.fuse.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.api.cluster.gateway.agent.GatewayAgent;
import xit.gateway.constant.RedisKey;
import xit.gateway.api.fuse.Fuse;
import xit.gateway.api.service.ConfigService;
import xit.gateway.deacon.cluster.gateway.container.impl.GlobalGatewayContainer;
import xit.gateway.pojo.CallLog;
import xit.gateway.utils.RedisUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DefaultFuse implements Fuse {
    private final AtomicInteger FUSING_THRESHOLD;
    private final AtomicInteger FUSING_TIMEOUT;
    private TimeUnit FUSING_TIMEUNIT;
    private final ConfigService configService;
    private final GlobalGatewayContainer gatewayContainer;
    private final GatewayAgent gatewayAgent;
    private final Logger logger = LoggerFactory.getLogger(DefaultFuse.class);

    @Autowired
    public DefaultFuse(ConfigService configService, GlobalGatewayContainer gatewayContainer, GatewayAgent gatewayAgent){
        this.configService = configService;
        this.gatewayContainer = gatewayContainer;
        this.gatewayAgent = gatewayAgent;
        this.FUSING_THRESHOLD = new AtomicInteger(-1);
        this.FUSING_TIMEOUT = new AtomicInteger(-1);
        flush();
    }

    @Override
    public void fuseIfNecessary(CallLog record) {
        if (record.getSuccess()){
            return;
        }

        String routeId = record.getRouteId();
        Integer fuseCount;

        fuseCount = RedisUtils.get(RedisKey.FUSE_COUNT.extend(routeId), Integer.class);
        fuseCount = fuseCount == null ? 1 : fuseCount + 1;
        if (fuseCount >= FUSING_THRESHOLD.get()){
            // 下达熔断通知
            logger.info("熔断路由：" + record.getRouteDesc());
            Optional.ofNullable(gatewayContainer.get(record.getGatewayId()))
                    .ifPresent(gateway -> gatewayAgent.disableRoute(
                            gateway,
                            record.getServiceName(), record.getRouteId()
                    ));
        }
        RedisUtils.set(RedisKey.FUSE_COUNT.extend(routeId), fuseCount, FUSING_TIMEOUT.get(), FUSING_TIMEUNIT);
    }

    @Override
    public void flush() {
        String fusingThresholdStr = configService.get("fusing_threshold").block();
        int fusingThreshold = StringUtils.isBlank(fusingThresholdStr) ? 5 : Integer.parseInt(fusingThresholdStr);
        String fusingTimeoutStr = configService.get("fusing_timeout").block();
        int fusingTimeout = StringUtils.isBlank(fusingTimeoutStr) ? 5 : Integer.parseInt(fusingTimeoutStr);
        String fusingTimeUnitStr = configService.get("fusing_timeunit").block();

        FUSING_TIMEUNIT = StringUtils.isBlank(fusingTimeUnitStr) ? TimeUnit.SECONDS : TimeUnit.valueOf(fusingTimeUnitStr);
        setAtomicInteger(fusingThreshold, FUSING_THRESHOLD);
        setAtomicInteger(fusingTimeout, FUSING_TIMEOUT);

    }

    private void setAtomicInteger(int val, AtomicInteger target){
        while (true){
            if (target.compareAndSet(target.get(), val)){
                break;
            }
        }
    }
}
