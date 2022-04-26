package xit.gateway.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "xit.gateway")
@EnableTransactionManagement
@EnableJpaRepositories
@EntityScan("xit.gateway.pojo")
public class TorchGameGatewayAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(TorchGameGatewayAdminApplication.class);
    }
}
