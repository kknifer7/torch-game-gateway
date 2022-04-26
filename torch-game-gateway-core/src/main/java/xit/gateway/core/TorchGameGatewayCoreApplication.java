package xit.gateway.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import xit.gateway.core.starter.GatewayStarter;

@SpringBootApplication(scanBasePackages = "xit.gateway")
public class TorchGameGatewayCoreApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(TorchGameGatewayCoreApplication.class);
        GatewayStarter gatewayStarter = (GatewayStarter) ac.getBean("gatewayStarter");

        gatewayStarter.start();
    }
}
