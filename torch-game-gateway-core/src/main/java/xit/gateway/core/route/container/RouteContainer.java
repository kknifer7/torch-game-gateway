package xit.gateway.core.route.container;

import xit.gateway.pojo.Route;

import java.util.List;

/**
 * @author Knifer
 * Description: 全局路由容器。
 * Date: 2022/03/27
 */
public interface RouteContainer {
    /**
     * 放置路由
     * @param route 路由
     */
    void put(Route route);

    /**
     * 放置路由
     * @param routeList 路由列表
     */
    void putAll(List<Route> routeList);

    /**
     * 获取路由
     * @param primaryKey 路由的唯一标识（ID或Name）
     * @return 路由
     */
    List<Route> get(String primaryKey);

    /**
     * 检查是否存在路由
     * @param primaryKey 路由的唯一标识（ID或Name）
     * @return 结果
     */
    boolean contains(String primaryKey);

    /**
     * 判空
     * @return 结果
     */
    boolean isEmpty();
}
