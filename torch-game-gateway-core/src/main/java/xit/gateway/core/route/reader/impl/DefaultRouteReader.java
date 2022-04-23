package xit.gateway.core.route.reader.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import xit.gateway.api.route.reader.RouteReader;
import xit.gateway.pojo.Route;
import xit.gateway.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

@Component
public class DefaultRouteReader implements RouteReader {
    @Override
    public List<Route> readRoutesFromJSON(String filePath) throws IOException {
        return JsonUtils.readValue(filePath, new TypeReference<>(){});
    }
}
