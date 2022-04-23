package xit.gateway.api.route.reader;

import xit.gateway.pojo.Route;

import java.io.IOException;
import java.util.List;

/**
 * @author Knifer
 * Description: 路由信息读取器。用于从各处读取路由信息
 * Date: 2022/03/25
 */
public interface RouteReader {
    List<Route> readRoutesFromJSON(String filePath) throws IOException;
}
