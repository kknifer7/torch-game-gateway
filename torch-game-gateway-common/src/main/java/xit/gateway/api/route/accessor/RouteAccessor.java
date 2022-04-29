package xit.gateway.api.route.accessor;

import xit.gateway.pojo.Route;

import java.util.Collection;

/**
 * @author Knifer
 * Description: 路由信息存取器。用于直接存取变更项目中的路由条目，使其生效
 * Date: 2022/04/12
 */
public interface RouteAccessor {
    /**
     * 将路由挂载到项目中使其生效
     * @param routes 路由列表
     */
    void loadRoutes(Collection<Route> routes);

    /**
     * 更新路由
     * @param route 新路由
     */
    void updateRoute(Route route);

    /**
     * 禁用路由
     * @param serviceId 服务名称
     * @param routeId 路由ID
     */
    void disableRoute(String serviceId, String routeId);

    /**
     * 删除路由
     * @param serviceId 服务名称
     * @param routeId 路由ID
     */
    void removeRoute(String serviceId, String routeId);

    /**
     * 删除服务下的所有路由
     * @param serviceId 服务名称
     */
    void removeAllRoute(String serviceId);
}
