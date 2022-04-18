package xit.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xit.gateway.core.fuse.Fuse;
import xit.gateway.core.fuse.impl.DefaultFuse;

import java.lang.reflect.InvocationTargetException;

@Configuration
public class DeaconConfig {
    private final Logger logger = LoggerFactory.getLogger(DeaconConfig.class);

    @Bean
    public Fuse fuse(@Value("${torch.gateway.deacon.fuse.fuse-class}") String fuseClass){
        Fuse fuse;

        try {
            fuse = (Fuse) Class.forName(fuseClass).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            logger.error("failed to create fuse, use default.");
            fuse = new DefaultFuse();
        }

        return fuse;
    }
}
