package xit.gateway.deacon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xit.gateway.deacon.fuse.Fuse;
import xit.gateway.deacon.fuse.impl.DefaultFuse;
import xit.gateway.deacon.service.ConfigService;

import java.lang.reflect.InvocationTargetException;

@Configuration
public class DeaconConfig {
    private final Logger logger = LoggerFactory.getLogger(DeaconConfig.class);
    private final ConfigService configService;

    @Autowired
    public DeaconConfig(ConfigService configService) {
        this.configService = configService;
    }

    @Bean
    public Fuse fuse(@Value("${torch.gateway.deacon.fuse.fuse-class}") String fuseClass){
        Fuse fuse;

        try {
            fuse = (Fuse) Class.forName(fuseClass).getConstructor(ConfigService.class).newInstance(configService);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            logger.error("failed to create fuse, use default.");
            fuse = new DefaultFuse(configService);
        }

        return fuse;
    }
}
