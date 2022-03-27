package xit.gateway.exception.system;

public class RouteResolvingException extends SystemException{
    public RouteResolvingException(){
        super();
    }

    public RouteResolvingException(String detailMessage){
        super(detailMessage);
    }
}
