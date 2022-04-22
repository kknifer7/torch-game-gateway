package xit.gateway.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import xit.gateway.core.starter.GatewayStarter;

@SpringBootApplication
public class TorchGameGatewayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(TorchGameGatewayApplication.class);
        GatewayStarter gatewayStarter = (GatewayStarter) ac.getBean("gatewayStarter");

        gatewayStarter.start();
    }
}
