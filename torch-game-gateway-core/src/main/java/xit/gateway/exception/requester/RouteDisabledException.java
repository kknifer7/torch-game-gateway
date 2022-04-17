package xit.gateway.exception.requester;

public class RouteDisabledException extends RequesterException{
    public RouteDisabledException(String detailMessage, String requestPath) {
        super(detailMessage, requestPath);
    }
}
