package xit.gateway.core.exception;

public class IllegalValveOperationException extends ValveException{
    public IllegalValveOperationException() {
        super();
    }

    public IllegalValveOperationException(String detailMessage){
        super(detailMessage);
    }
}
