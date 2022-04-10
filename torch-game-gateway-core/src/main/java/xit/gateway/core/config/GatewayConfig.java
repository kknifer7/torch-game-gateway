package xit.gateway.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xit.gateway.core.loadbalancer.Loadbalancer;

import java.lang.reflect.InvocationTargetException;

@Configuration
public class GatewayConfig {
    @Bean
    public Loadbalancer loadbalancer(
            @Value("${torch.gateway.loadbalance.loadbalancer-class}")
            String className
    ) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (Loadbalancer) Class.forName(className).getConstructor().newInstance();
    }
}
