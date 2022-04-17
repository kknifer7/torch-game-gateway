package xit.gateway.exception.requester;

public class RequesterNotFoundException extends RequesterException{
    public RequesterNotFoundException(String detailMessage, String requestPath) {
        super(detailMessage, requestPath);
    }
}
