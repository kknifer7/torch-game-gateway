package xit.gateway.core.request.requester;

import xit.gateway.core.request.requester.context.RequesterContext;
import xit.gateway.core.request.requester.context.impl.DefaultRequesterContext;

/**
 * @author Knifer
 * Description: Rest 请求发送器。
 * Date: 2022/04/10
 */
public abstract class AbstractRequester implements Requester{
    protected final RequesterContext requesterContext;

    public AbstractRequester() {
        this.requesterContext = new DefaultRequesterContext();
    }

    @Override
    public RequesterContext getRequesterContext() {
        return requesterContext;
    }
}
