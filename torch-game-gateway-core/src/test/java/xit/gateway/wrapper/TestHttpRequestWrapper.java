package xit.gateway.wrapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestHttpRequestWrapper {
    @Test
    void testHttpRouteWrapperInstantiation(){
        /*RouteReader routeReader = new DefaultRouteReader();
        List<HttpRoute> httpRoutes = null;
        HttpRequestWrapper wrapper;

        try {
            httpRoutes = routeReader.loadHttpRouteFromJSON("test-data/http-routes.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertNotEquals(null, httpRoutes);
        wrapper = new HttpRequestWrapper(httpRoutes.get(0));
        Assertions.assertNotEquals(null, wrapper.getDesc());*/
    }
}
