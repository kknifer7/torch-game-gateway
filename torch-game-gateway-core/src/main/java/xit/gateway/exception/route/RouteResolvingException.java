package xit.gateway.exception.route;

public class RouteResolvingException extends RuntimeException {
    public RouteResolvingException(){
        super();
    }

    public RouteResolvingException(String detailMessage){
        super(detailMessage);
    }
}
