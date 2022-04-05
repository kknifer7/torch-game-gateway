package xit.gateway.exception.route;

public class RouteLoadingException extends RuntimeException{
    public RouteLoadingException(){
        super();
    }

    public RouteLoadingException(String detailMessage){
        super(detailMessage);
    }
}
