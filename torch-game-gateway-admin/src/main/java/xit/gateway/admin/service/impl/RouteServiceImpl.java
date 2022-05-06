package xit.gateway.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.domain.Route;
import xit.gateway.admin.repository.RouteRepository;
import xit.gateway.admin.repository.ServiceRepository;
import xit.gateway.admin.service.RouteService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public long count() {
        return routeRepository.count();
    }
    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public Optional<Route> findById(String id) {
        return routeRepository.findById(id);
    }


    @Override
    public Route create(Route resources) {
        Optional<xit.gateway.admin.domain.Service> service = serviceRepository.findById(resources.getService().getId());
        if (service.isPresent()) {
            resources.setServiceName(service.get().getName());
        }

        Route save = routeRepository.save(resources);
        return save;
    }

    @Override
    public Route update(Route resources) {
        Route route = routeRepository.findById(resources.getId()).orElseGet(Route::new);
        Optional<xit.gateway.admin.domain.Service> service = serviceRepository.findById(resources.getService().getId());
        if (service.isPresent()) {
            route.setServiceName(service.get().getName());
        }

        route.setRemark(resources.getRemark());
        route.setProtocol(resources.getProtocol());
        route.setHost(resources.getHost());
        route.setPort(resources.getPort());
        route.setUrl(resources.getUrl());
        route.setExtra(resources.getExtra());
        route.setStatus(resources.getStatus());
        Route save = routeRepository.save(route);
        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        routeRepository.deleteById(id);
    }
}
