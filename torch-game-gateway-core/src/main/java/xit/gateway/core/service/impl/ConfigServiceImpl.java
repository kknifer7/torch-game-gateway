package xit.gateway.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xit.gateway.core.dao.ConfigDAO;
import xit.gateway.api.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final ConfigDAO configDAO;

    @Autowired
    public ConfigServiceImpl(ConfigDAO configDAO) {
        this.configDAO = configDAO;
    }

    @Override
    public Mono<String> get(String kee) {
        return configDAO.findByKee(kee).flatMap(config -> Mono.just(config.getVal()));
    }
}
