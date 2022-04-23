package xit.gateway.core.valve.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xit.gateway.api.route.accessor.RouteAccessor;
import xit.gateway.api.route.reader.RouteReader;
import xit.gateway.api.valve.ProcessCoreValve;
import xit.gateway.exception.route.RouteLoadingException;
import xit.gateway.pojo.Route;

import java.io.IOException;
import java.util.List;

/**
 * @author knifer
 * Description: 路由加载阀门
 * Date: 2022/03/28
 */
@Component
public class RouteInitializationLoadingValve extends ProcessCoreValve {
    private final RouteReader routeReader;
    private final RouteAccessor routeAccessor;

    @Value("${torch.gateway.init.routes-json-path}")
    private String initRoutesJsonPath;

    @Autowired
    public RouteInitializationLoadingValve(RouteReader routeReader, RouteAccessor routeAccessor) {
        this.routeReader = routeReader;
        this.routeAccessor = routeAccessor;
    }

    @Override
    protected void work() {
        List<Route> routes;

        try {
            routes = routeReader.readRoutesFromJSON(initRoutesJsonPath);
        } catch (IOException e) {
            throw new RouteLoadingException("routeing load failed: " + e.getMessage());
        }

        if (!CollectionUtils.isEmpty(routes)){
            routeAccessor.loadRoutes(routes);
        }
    }
}
