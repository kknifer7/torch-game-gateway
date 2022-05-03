package xit.gateway.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.Config;
import xit.gateway.admin.repository.ConfigRepository;
import xit.gateway.admin.service.ConfigService;
import xit.gateway.exception.requester.BadRequestException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {
    private final ConfigRepository configRepository;


    @Override
    public List<Config> findAll() {
        return configRepository.findAll();
    }


    @Override
    public void create(Config resources) {
        if (configRepository.findByName(resources.getName()) != null) {
            throw new BadRequestException("配置已存在");
        }
        configRepository.save(resources);
    }

    @Override
    public void update(Config resources) {
        Config config = configRepository.findById(resources.getId()).orElseGet(Config::new);

        config.setName(resources.getName());
        config.setKee(resources.getKee());
        config.setVal(resources.getVal());
        config.setRemark(resources.getRemark());

        configRepository.save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        configRepository.deleteById(id);
    }

}
