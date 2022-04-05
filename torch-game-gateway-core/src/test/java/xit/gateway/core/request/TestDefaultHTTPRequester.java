package xit.gateway.core.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xit.gateway.core.request.requester.HttpRequester;
import xit.gateway.core.request.requester.impl.DefaultHttpRequester;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.core.route.reader.RouteReader;
import xit.gateway.core.route.reader.impl.DefaultRouteReader;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TestDefaultHTTPRequester {
    @Test
    void test(){
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
