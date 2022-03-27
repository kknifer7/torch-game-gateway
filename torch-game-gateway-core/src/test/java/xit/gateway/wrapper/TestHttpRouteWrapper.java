package xit.gateway.wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.core.reader.RouteReader;
import xit.gateway.core.reader.impl.DefaultRouteReader;
import xit.gateway.pojo.HttpRoute;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TestHttpRouteWrapper {
    @Test
    void testHttpRouteWrapperInstantiation(){
        RouteReader routeReader = new DefaultRouteReader();
        List<HttpRoute> httpRoutes = null;
        HttpRouteWrapper wrapper;

        try {
            httpRoutes = routeReader.loadHttpRouteFromJSON("test-data/http-routes.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertNotEquals(null, httpRoutes);
        wrapper = new HttpRouteWrapper(httpRoutes.get(0));
        Assertions.assertNotEquals(null, wrapper.getDesc());
    }
}
