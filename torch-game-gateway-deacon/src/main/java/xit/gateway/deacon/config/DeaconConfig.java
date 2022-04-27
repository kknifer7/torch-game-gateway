package xit.gateway.deacon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xit.gateway.api.cluster.gateway.agent.GatewayAgent;
import xit.gateway.api.cluster.heartbeatserver.HeartBeatServer;
import xit.gateway.api.fuse.Fuse;
import xit.gateway.api.request.container.RequestContextContainer;
import xit.gateway.api.route.loadbalancer.Loadbalancer;
import xit.gateway.deacon.cluster.gateway.container.impl.GlobalGatewayContainer;
import xit.gateway.deacon.fuse.impl.DefaultFuse;
import xit.gateway.api.service.ConfigService;
import xit.gateway.loadbalancer.impl.NoLoadbalancer;
import xit.gateway.request.container.impl.GlobalRequestContextContainer;

import java.lang.reflect.InvocationTargetException;

@Configuration
public class DeaconConfig {
    private final Logger logger = LoggerFactory.getLogger(DeaconConfig.class);
    private final ConfigService configService;
    private final GlobalGatewayContainer gatewayContainer;
    private final GatewayAgent gatewayAgent;

    @Autowired
    public DeaconConfig(HeartBeatServer heartBeatServer, ConfigService configService, GlobalGatewayContainer gatewayContainer, GatewayAgent gatewayAgent) {
        this.configService = configService;
        this.gatewayContainer = gatewayContainer;
        this.gatewayAgent = gatewayAgent;
        heartBeatServer.start();
    }

    @Bean
    public Fuse fuse(@Value("${torch.gateway.deacon.fuse.fuse-class}") String fuseClass){
        Fuse fuse;

        try {
            fuse = (Fuse) Class.forName(fuseClass)
                    .getConstructor(ConfigService.class, GlobalGatewayContainer.class, GatewayAgent.class)
                    .newInstance(configService, gatewayContainer, gatewayAgent);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("failed to create fuse, use default.");
            fuse = new DefaultFuse(configService, gatewayContainer, gatewayAgent);
        }

        return fuse;
    }

    @Bean
    public Loadbalancer loadbalancer(
            @Value("${torch.gateway.deacon.loadbalance.loadbalancer-class}")
                    String className
    ){
        try {
            return (Loadbalancer) Class.forName(className).getConstructor().newInstance();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            logger.warn("failed to create loadbalancer, working without loadbalancer~");
        }

        return new NoLoadbalancer();
    }

    @Bean
    public RequestContextContainer requestContextContainer(){
        return new GlobalRequestContextContainer();
    }
}
