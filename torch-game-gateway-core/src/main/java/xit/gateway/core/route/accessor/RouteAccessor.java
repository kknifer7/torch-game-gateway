package xit.gateway.core.route.accessor;

import xit.gateway.core.route.container.impl.RouteGroup;

import java.util.List;

/**
 * @author Knifer
 * Description: 路由信息存取器。用于同第三方挂载、存储路由
 * Date: 2022/04/12
 */
public interface RouteAccessor {
    /**
     * 将路由组挂载到项目中使其生效
     * @param routeGroupList 路由组列表
     */
    void mountRouteGroups(List<RouteGroup> routeGroupList);

    /**
     * 从外界读取路由组并挂载到项目中使其生效
     */
    void loadRouteGroups();
}
