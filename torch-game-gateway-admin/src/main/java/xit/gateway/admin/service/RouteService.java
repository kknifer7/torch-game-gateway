package xit.gateway.admin.service;

import xit.gateway.pojo.Route;

import java.util.List;
import java.util.Set;

public interface RouteService {

    List<Route> findAll();


    void create(Route resources);

    void update(Route resources);

    void delete(Set<Long> ids);
}
