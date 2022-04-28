package xit.gateway.exception.user;

public class AccessForbiddenException extends RuntimeException{
    public AccessForbiddenException(){
        super();
    }

    public AccessForbiddenException(String detailMessage){
        super(detailMessage);
    }
}
