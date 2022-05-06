package xit.gateway.admin.service;

import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.Route;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RouteService {

    long count();

    List<Route> findAll();


    Optional<Route> findById(String id);

    Route create(Route resources);

    Route update(Route resources);

    @Transactional(rollbackFor = Exception.class)
    void delete(String id);
}
