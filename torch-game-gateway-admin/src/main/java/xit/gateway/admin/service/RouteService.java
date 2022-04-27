package xit.gateway.admin.service;

import xit.gateway.admin.domain.Route;

import java.util.List;
import java.util.Set;

public interface RouteService {

    List<Route> findAll();


    void create(Route resources);

    void update(Route resources);

    void delete(Set<String> ids);
}
