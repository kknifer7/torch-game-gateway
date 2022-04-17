package xit.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xit.gateway.core.fuse.Fuse;
import xit.gateway.core.fuse.impl.DefaultFuse;

@Configuration
public class DeaconConfig {
    @Bean
    public Fuse fuse(){
        return new DefaultFuse();
    }
}
