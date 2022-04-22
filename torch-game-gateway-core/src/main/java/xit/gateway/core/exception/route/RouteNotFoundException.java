package xit.gateway.core.exception.route;

import xit.gateway.core.exception.requester.RequesterException;

public class RouteNotFoundException extends RequesterException {
    public RouteNotFoundException(String detailMessage, String requestPath) {
        super(detailMessage, requestPath);
    }
}
