package xit.gateway.core.route.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.core.route.reader.impl.DefaultRouteReader;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.pojo.Route;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TestDefaultRouteReader {
    @Test
    void testLoadJSON(){
        RouteReader routeReader = new DefaultRouteReader();
        List<Route> httpRoutes = null;
        List<RouteGroup> routeGroups = null;

        try {
            //httpRoutes = routeReader.readHttpRouteFromJSON("test-data/http-routes.json");
            routeGroups = routeReader.readRouteGroupFromJSON("../test-data/route-groups.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Assertions.assertNotEquals(null, httpRoutes);
        Assertions.assertNotEquals(null, routeGroups);
    }
}
