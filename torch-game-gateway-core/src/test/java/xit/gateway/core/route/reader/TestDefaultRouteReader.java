package xit.gateway.core.route.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.api.route.reader.RouteReader;
import xit.gateway.core.route.reader.impl.DefaultRouteReader;
import xit.gateway.pojo.Route;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TestDefaultRouteReader {
    @Test
    void testLoadJSON(){
        RouteReader routeReader = new DefaultRouteReader();
        List<Route> routes = null;

        try {
            //httpRoutes = routeReader.readHttpRouteFromJSON("test-data/http-routes.json");
            routes = routeReader.readRoutesFromJSON("../test-data/route.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Assertions.assertNotEquals(null, httpRoutes);
        Assertions.assertNotEquals(null, routes);
    }
}
