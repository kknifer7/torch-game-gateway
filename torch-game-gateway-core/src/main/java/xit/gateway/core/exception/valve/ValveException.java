package xit.gateway.core.exception.valve;

public class ValveException extends RuntimeException {
    public ValveException(){
        super();
    }

    public ValveException(String detailMessage){
        super(detailMessage);
    }
}
