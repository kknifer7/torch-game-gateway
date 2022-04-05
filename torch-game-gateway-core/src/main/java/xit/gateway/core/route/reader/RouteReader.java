package xit.gateway.core.route.reader;

import xit.gateway.pojo.HttpRoute;
import xit.gateway.core.route.container.impl.RouteGroup;
import xit.gateway.pojo.RpcRoute;

import java.io.IOException;
import java.util.List;

/**
 * @author Knifer
 * Description: 路由信息读取器。用于从各处读取路由信息
 * Date: 2022/03/25
 */
public interface RouteReader {
    List<HttpRoute> loadHttpRouteFromJSON(String filePath) throws IOException;
    List<RpcRoute> loadRpcRouteFromJSON(String filePath) throws IOException;
    List<RouteGroup> loadRouteGroupFromJSON(String filePath) throws IOException;
}
