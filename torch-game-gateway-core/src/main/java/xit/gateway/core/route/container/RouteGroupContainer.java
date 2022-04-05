package xit.gateway.core.route.container;

import xit.gateway.core.route.container.impl.RouteGroup;

import java.util.List;

/**
 * @author Knifer
 * Description: 全局路由组容器。
 * Date: 2022/03/29
 */
public interface RouteGroupContainer {
    /**
     * 放置路由组
     * @param routeGroup 路由
     */
    void put(RouteGroup routeGroup);

    /**
     * 放置路由
     * @param routeGroupList 路由组列表
     */
    void putAll(List<RouteGroup> routeGroupList);

    /**
     * 获取路由
     * @param id 路由组ID
     * @return 路由
     */
    RouteGroup get(String id);

    /**
     * 检查是否存在路由
     * @param id 路由组ID
     * @return 结果
     */
    boolean contains(String id);

    /**
     * 判空
     * @return 结果
     */
    boolean isEmpty();

    /**
     * 对重复RouteGroup添加时的策略
     * @param routeGroup 重复的要添加的RouteGroup
     */
    void handleRouteGroupDuplicate(RouteGroup routeGroup);
}
