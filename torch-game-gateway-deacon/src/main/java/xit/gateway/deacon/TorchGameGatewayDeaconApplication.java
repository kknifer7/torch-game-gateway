package xit.gateway.deacon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "xit.gateway")
public class TorchGameGatewayDeaconApplication {
    public static void main(String[] args) {
        SpringApplication.run(TorchGameGatewayDeaconApplication.class);
    }
}
