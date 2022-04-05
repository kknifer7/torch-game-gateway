package xit.gateway.exception.requester;

public class RequesterDuplicatedException extends RuntimeException{
    public RequesterDuplicatedException(){
        super();
    }

    public RequesterDuplicatedException(String detailMessage){
        super(detailMessage);
    }
}
