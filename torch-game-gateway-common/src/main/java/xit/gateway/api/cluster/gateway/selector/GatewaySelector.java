package xit.gateway.api.cluster.gateway.selector;

import xit.gateway.pojo.Gateway;

import java.util.Collection;

/**
 * @author Knifer
 * Date: 2022/04/25
 * Describe: 网关选择器，用于从全局网关容器中选取合适网关（必要时选取备用集群网关）
 */
public interface GatewaySelector {
    Collection<Gateway> select();
    void refresh();
}
