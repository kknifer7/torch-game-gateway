package xit.gateway.core.exception.requester;

public class RequesterException extends RuntimeException{
    protected final String requestPath;

    public RequesterException(String detailMessage, String requestPath){
        super(detailMessage);
        this.requestPath = requestPath;
    }

    public String getRequestPath(){
        return requestPath;
    }
}
