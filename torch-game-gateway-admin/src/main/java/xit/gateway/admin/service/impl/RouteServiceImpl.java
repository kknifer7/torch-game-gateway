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
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public List<Route> findAll() {
//        xit.gateway.admin.domain.Service service = serviceRepository.findByName("service-01");
//
//        Route route = new Route();
//        route.setService(service);
//        Example<Route> routeExample = Example.of(route);
        List<Route> all = routeRepository.findAll();

        return all;
    }


    @Override
    public void create(Route resources) {
        Optional<xit.gateway.admin.domain.Service> service = serviceRepository.findById(resources.getService().getId());
        if (service.isPresent()) {
            resources.setServiceName(service.get().getName());
        }

        routeRepository.save(resources);
        // TODO: 向deacon进行同步操作
    }

    @Override
    public void update(Route resources) {
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
        routeRepository.save(route);

        // TODO: 向deacon进行同步操作
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        routeRepository.deleteAllByIdIn(ids);
    }
}
