package xit.gateway.exception.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String detailMessage){
        super(detailMessage);
    }
}
