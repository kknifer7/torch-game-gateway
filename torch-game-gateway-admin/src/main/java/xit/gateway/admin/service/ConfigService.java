package xit.gateway.admin.service;

import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.Config;

import java.util.List;

public interface ConfigService {
    List<Config> findAll();

    void create(Config resources);

    void update(Config resources);

    @Transactional(rollbackFor = Exception.class)
    void delete(Long id);
}
