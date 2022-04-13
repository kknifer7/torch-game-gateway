package xit.gateway.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xit.gateway.core.loadbalancer.Loadbalancer;
import xit.gateway.core.loadbalancer.impl.NoLoadbalancer;

@Configuration
public class GatewayConfig {
    private final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    @Bean
    public Loadbalancer loadbalancer(
            @Value("${torch.gateway.loadbalance.loadbalancer-class}")
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
}
