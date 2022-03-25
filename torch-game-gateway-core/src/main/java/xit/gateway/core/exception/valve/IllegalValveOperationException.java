package xit.gateway.core.exception.valve;

public class IllegalValveOperationException extends ValveException{
    public IllegalValveOperationException() {
        super();
    }

    public IllegalValveOperationException(String detailMessage){
        super(detailMessage);
    }
}
