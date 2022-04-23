package xit.gateway.exception.valve;

public class IllegalValveOperationException extends ValveException{
    public IllegalValveOperationException() {
        super();
    }

    public IllegalValveOperationException(String detailMessage){
        super(detailMessage);
    }
}
