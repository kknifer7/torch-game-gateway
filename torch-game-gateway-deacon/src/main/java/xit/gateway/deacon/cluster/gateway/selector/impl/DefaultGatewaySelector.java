package xit.gateway.deacon.cluster.gateway.selector.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xit.gateway.api.cluster.gateway.selector.GatewaySelector;
import xit.gateway.api.service.ConfigService;
import xit.gateway.deacon.cluster.gateway.container.impl.GlobalGatewayContainer;
import xit.gateway.pojo.Gateway;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DefaultGatewaySelector implements GatewaySelector {
    private final GlobalGatewayContainer gatewayContainer;
    private final ConfigService configService;
    private int backupEnableThreshold;

    @Autowired
    public DefaultGatewaySelector(GlobalGatewayContainer gatewayContainer, ConfigService configService){
        this.gatewayContainer = gatewayContainer;
        this.configService = configService;
        refresh();
    }

    @Override
    public Collection<Gateway> select() {
        // 当前使用的非备用网关数目小于等于阈值，则启用备用网关
        boolean enableBackup = getEnabledGatewayStream()
                .filter(gateway -> !gateway.getBackup() && gateway.getStatus())
                .count() <= backupEnableThreshold;

        return getEnabledGatewayStream()
                .filter(gateway -> (!gateway.getBackup() || enableBackup) && gateway.getStatus())
                .collect(Collectors.toList());
    }

    private Stream<Gateway> getEnabledGatewayStream(){
        return gatewayContainer.getAll().stream().filter(Gateway::getStatus);
    }

    @Override
    public void refresh() {
        String backupEnableThresholdStr = configService.get("backup_enable_threshold").block();

        backupEnableThreshold =
                StringUtils.isBlank(backupEnableThresholdStr) ? 5 : Integer.parseInt(backupEnableThresholdStr);
    }
}
