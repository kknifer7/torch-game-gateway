package xit.gateway.core.context.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xit.gateway.api.context.GatewayContext;
import xit.gateway.api.route.limiter.manager.LimiterManager;
import xit.gateway.core.limiter.container.impl.GlobalLimiterContainer;
import xit.gateway.core.request.container.GlobalRequesterContainer;
import xit.gateway.pojo.Gateway;
import xit.gateway.request.container.impl.GlobalRequestContextContainer;
import xit.gateway.core.route.container.impl.GlobalRoutesContainer;
import xit.gateway.utils.UUIDUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Knifer
 * Description: 项目上下文
 * Date: 2022/03/25
 */
@Component
public class DefaultGatewayContext implements GatewayContext {
    private final GlobalRequestContextContainer routeRequestContextContainer;
    private final GlobalRequesterContainer requesterContainer;
    private final GlobalRoutesContainer serviceRoutesContainer;
    private final GlobalLimiterContainer limiterContainer;
    private final LimiterManager limiterManager;
    private Gateway gateway;

    @Autowired
    public DefaultGatewayContext(
            GlobalRequestContextContainer routeRequestContextContainer,
            GlobalRequesterContainer requesterContainer,
            GlobalRoutesContainer serviceRoutesContainer,
            GlobalLimiterContainer limiterContainer,
            LimiterManager limiterManager,
            @Value("${torch.gateway.call-trace.deacon.heart-beat.backup}")
                    boolean gatewayBackup,
            @Value("${torch.gateway.call-trace.deacon.heart-beat.use-ssl}")
                    boolean gatewayUseSSL,
            @Value("${server.port}")
                    int gatewayPort
    ){
        String id;

        this.routeRequestContextContainer = routeRequestContextContainer;
        this.requesterContainer = requesterContainer;
        this.serviceRoutesContainer = serviceRoutesContainer;
        this.limiterContainer = limiterContainer;
        this.limiterManager = limiterManager;
        id = UUIDUtils.getRandom();
        try {
            this.gateway = new Gateway(
                    id, "Gateway|" + id,
                    InetAddress.getLocalHost().getHostAddress(),
                    gatewayPort,
                    gatewayUseSSL,
                    gatewayBackup,
                    true,
                    0, 0, 0, 0,  // 性能数据由心跳客户端填充
                    null
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GlobalRequesterContainer requesterContainer() {
        return requesterContainer;
    }

    @Override
    public GlobalRequestContextContainer routeRequestContextContainer() {
        return routeRequestContextContainer;
    }

    @Override
    public GlobalRoutesContainer routesContainer() {
        return serviceRoutesContainer;
    }

    @Override
    public GlobalLimiterContainer limiterContainer() {
        return limiterContainer;
    }

    @Override
    public LimiterManager limiterManager() {
        return limiterManager;
    }

    @Override
    public Gateway gateway() {
        return gateway;
    }
}
