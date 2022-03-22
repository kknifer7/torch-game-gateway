package xit.gateway.core.exception;

public class ValveException extends RuntimeException{
    public ValveException() {
        super();
    }

    public ValveException(String detailMessage){
        super(detailMessage);
    }
}
