package xit.gateway.admin.service;

import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.Route;

import java.util.List;
import java.util.Set;

public interface RouteService {

    List<Route> findAll();


    void create(Route resources);

    void update(Route resources);

    @Transactional(rollbackFor = Exception.class)
    void delete(String id);
}
