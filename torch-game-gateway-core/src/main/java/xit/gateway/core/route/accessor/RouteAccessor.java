package xit.gateway.core.route.accessor;

import xit.gateway.core.route.container.impl.RouteGroup;

import java.util.List;

/**
 * @author Knifer
 * Description: 路由信息存取器。用于直接存取变更项目中的路由条目，使其生效
 * Date: 2022/04/12
 */
public interface RouteAccessor {
    /**
     * 将路由组挂载到项目中使其生效
     * @param routeGroupList 路由组列表
     */
    void loadRouteGroups(List<RouteGroup> routeGroupList);
}
