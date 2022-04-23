package xit.gateway.core.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xit.gateway.api.route.loadbalancer.Loadbalancer;
import xit.gateway.api.route.reader.RouteReader;

import java.io.IOException;

@SpringBootTest
public class TestDefaultHTTPRequester {
    @Autowired
    private RouteReader routeReader;
    @Autowired
    private Loadbalancer loadbalancer;

    @Test
    void test() throws IOException {
        //DefaultHttpRequester requester = new DefaultHttpRequester(routeReader.readRouteGroupFromJSON("test-data/route-groups.json").get(1), loadbalancer);
        /*RouteReader routeReader = new DefaultRouteReader();
        List<RouteGroup> routeGroups = null;
        HttpRequester requester;
        CorePublisher<?> result;

        try {
            routeGroups = routeReader.loadRouteGroupFromJSON("test-data/route-groups.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertNotEquals(null, routeGroups);
        requester = new DefaultHttpRequester(routeGroups.get(0));
        result = requester.invoke("route-test");
        if (result instanceof Mono){
            Assertions.assertEquals("success", ((Mono<?>)result).block());
        }else if (result instanceof Flux){
            Assertions.assertEquals("success", ((Flux<?>)result).blockFirst());
        }*/
    }
}
