package xit.gateway.core.request.requester.factory;

import xit.gateway.api.request.requester.Requester;
import xit.gateway.core.request.requester.impl.DefaultHttpRequester;
import xit.gateway.core.request.requester.impl.DefaultRpcRequester;
import xit.gateway.pojo.Route;

public class RequesterFactory {
    public static Requester get(Route route) {
        Requester res = null;

        switch (route.getProtocol()){
            case HTTP:
                res = new DefaultHttpRequester(route);
                break;
            case RPC:
                res = new DefaultRpcRequester(route);
                break;
        }

        return res;
    }
}
