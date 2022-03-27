package xit.gateway.core.reader.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import xit.gateway.core.reader.RouteReader;
import xit.gateway.pojo.HttpRoute;
import xit.gateway.pojo.RpcRoute;
import xit.gateway.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

public class DefaultRouteReader implements RouteReader {
    @Override
    public List<HttpRoute> loadHttpRouteFromJSON(String filePath) throws IOException {
        return JsonUtils.readValue(filePath, new TypeReference<>(){});
    }

    @Override
    public List<RpcRoute> loadRpcRouteFromJSON(String filePath) throws IOException {
        return JsonUtils.readValue(filePath, new TypeReference<>(){});
    }
}
