package xit.gateway.core.exception.route;

import xit.gateway.core.exception.requester.RequesterException;

public class RouteDisabledException extends RequesterException {
    public RouteDisabledException(String detailMessage, String requestPath) {
        super(detailMessage, requestPath);
    }
}
