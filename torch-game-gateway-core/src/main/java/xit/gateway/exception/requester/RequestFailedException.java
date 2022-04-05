package xit.gateway.exception.requester;

public class RequestFailedException extends RuntimeException{
    public RequestFailedException(){
        super();
    }

    public RequestFailedException(String detailMessage){
        super(detailMessage);
    }
}
