package xit.gateway.core.route.reader.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import xit.gateway.core.route.reader.RouteReader;
import xit.gateway.pojo.HttpRoute;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.pojo.RpcRoute;
import xit.gateway.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

@Component
public class DefaultRouteReader implements RouteReader {
    @Override
    public List<HttpRoute> loadHttpRouteFromJSON(String filePath) throws IOException {
        return JsonUtils.readValue(filePath, new TypeReference<>(){});
    }

    @Override
    public List<RpcRoute> loadRpcRouteFromJSON(String filePath) throws IOException {
        return JsonUtils.readValue(filePath, new TypeReference<>(){});
    }

    @Override
    public List<RouteGroup> loadRouteGroupFromJSON(String filePath) throws IOException {
        return JsonUtils.readValue(filePath, new TypeReference<>(){});
    }
}
