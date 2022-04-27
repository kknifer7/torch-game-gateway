package xit.gateway.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xit.gateway.admin.repository.RouteRepository;
import xit.gateway.admin.service.RouteService;
import xit.gateway.admin.domain.Route;
import xit.gateway.exception.requester.BadRequestException;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }


    @Override
    public void create(Route resources) {
        if (routeRepository.findByName(resources.getName()) != null) {
            throw new BadRequestException("服务已存在");
        }

        routeRepository.save(resources);
    }

    @Override
    public void update(Route resources) {
        Route route = routeRepository.findByName(resources.getName());

        route.setName(resources.getName());
        route.setRemark(resources.getRemark());
        route.setProtocol(resources.getProtocol());
        route.setHost(resources.getHost());
        route.setPort(resources.getPort());
        route.setUrl(resources.getUrl());
        route.setExtra(resources.getExtra());
        routeRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        routeRepository.deleteAllByIdIn(ids);
    }
}
