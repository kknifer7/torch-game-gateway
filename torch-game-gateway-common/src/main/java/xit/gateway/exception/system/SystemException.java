package xit.gateway.exception.system;

public class SystemException extends RuntimeException{
    public SystemException(){
        super();
    }

    public SystemException(String detailMessage){
        super(detailMessage);
    }
}
