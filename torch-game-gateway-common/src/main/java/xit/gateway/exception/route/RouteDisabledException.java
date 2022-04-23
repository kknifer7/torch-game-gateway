package xit.gateway.exception.route;

import xit.gateway.exception.requester.RequesterException;

public class RouteDisabledException extends RequesterException {
    public RouteDisabledException(String detailMessage, String requestPath) {
        super(detailMessage, requestPath);
    }
}
