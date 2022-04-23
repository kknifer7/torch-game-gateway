package xit.gateway.exception.route;

import xit.gateway.exception.requester.RequesterException;

public class RouteNotFoundException extends RequesterException {
    public RouteNotFoundException(String detailMessage, String requestPath) {
        super(detailMessage, requestPath);
    }
}
