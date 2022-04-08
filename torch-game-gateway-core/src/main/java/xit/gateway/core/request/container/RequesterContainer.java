package xit.gateway.core.request.container;

import xit.gateway.core.request.requester.Requester;

/**
 * @author Knifer
 * Description: Requester容器
 * Date: 2022/04/03
 */
public interface RequesterContainer {
    void put(Requester requester);

    void putAll(Requester[] requesters);

    Requester get(String primary);

    boolean contains(String primaryKey);

    boolean isEmpty();
}
