package xit.gateway.exception.requester;

public class RequestFailedException extends RequesterException{

    public RequestFailedException(String detailMessage, String requestPath){
        super(detailMessage, requestPath);
    }
}
