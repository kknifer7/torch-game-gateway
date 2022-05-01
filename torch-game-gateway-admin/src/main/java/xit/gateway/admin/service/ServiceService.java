package xit.gateway.admin.service;


import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServiceService {
    List<Service> findAll();

    Optional<Service> findByName(String name);

    void create(Service resources);


    void update(Service resources);

    @Transactional(rollbackFor = Exception.class)
    void delete(Long id);
}