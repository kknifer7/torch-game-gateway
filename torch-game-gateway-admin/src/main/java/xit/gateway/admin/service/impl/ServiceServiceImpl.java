package xit.gateway.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.Service;
import xit.gateway.admin.repository.ServiceRepository;
import xit.gateway.admin.service.ServiceService;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        List<Service> all = serviceRepository.findAll();
        return all;
    }

    @Override
    public Optional<Service> findByName(String name) {
        return Optional.ofNullable(serviceRepository.findByName(name));
    }


    @Override
    public void create(Service resources) {
        serviceRepository.save(resources);
        // TODO: 向deacon进行同步操作
    }

    @Override
    public void update(Service resources) {
        Service service = serviceRepository.findById(resources.getId()).orElseGet(Service::new);

        service.setName(resources.getName());
        service.setApp(resources.getApp());
        service.setRemark(resources.getRemark());
        service.setRoutes(resources.getRoutes());
        service.setStatus(resources.getStatus());
        serviceRepository.save(service);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
