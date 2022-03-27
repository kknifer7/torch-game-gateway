package xit.gateway.core.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.core.reader.impl.DefaultRouteReader;
import xit.gateway.pojo.HttpRoute;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TestDefaultRouteReader {
    @Test
    void testLoadJSON(){
        RouteReader routeReader = new DefaultRouteReader();
        List<HttpRoute> httpRoutes = null;

        try {
            httpRoutes = routeReader.loadHttpRouteFromJSON("test-data/http-routes.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertNotEquals(null, httpRoutes);
    }
}
