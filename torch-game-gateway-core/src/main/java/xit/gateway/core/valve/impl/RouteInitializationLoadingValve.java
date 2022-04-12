package xit.gateway.core.valve.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.core.route.accessor.impl.RouteRedisAccessor;
import xit.gateway.core.route.reader.RouteReader;
import xit.gateway.core.valve.ProcessCoreValve;
import xit.gateway.exception.route.RouteLoadingException;

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
    private final RouteRedisAccessor routeRedisLoader;

    @Value("${torch.gateway.init.routes-json-path}")
    private String initRoutesJsonPath;

    @Autowired
    public RouteInitializationLoadingValve(RouteReader routeReader, RouteRedisAccessor routeRedisLoader) {
        this.routeReader = routeReader;
        this.routeRedisLoader = routeRedisLoader;
    }

    @Override
    protected void work() {
        List<RouteGroup> routeGroups;

        try {
            routeGroups = routeReader.loadRouteGroupFromJSON(initRoutesJsonPath);
        } catch (IOException e) {
            throw new RouteLoadingException("routeing load failed: " + e.getMessage());
        }

        if (routeGroups != null && !routeGroups.isEmpty()){
            routeRedisLoader.mountRouteGroups(routeGroups);
        }
    }
}
