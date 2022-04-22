package xit.gateway.core.request.requester;

import xit.gateway.core.pojo.Route;

/**
 * @author Knifer
 * Description: Rest 请求发送器。
 * Date: 2022/04/10
 */
public abstract class AbstractRequester implements Requester{
    protected final Route route;
    protected String key;

    public AbstractRequester(Route route) {
        this.route = route;
        this.key = route.getId();
    }

    @Override
    public String getKeyInContainer() {
        return key;
    }
}
