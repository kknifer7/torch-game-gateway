package xit.gateway.exception.user;

public class UserVerificationFailedException extends RuntimeException{
    public UserVerificationFailedException(){
        super();
    }

    public UserVerificationFailedException(String detailMessage){
        super(detailMessage);
    }
}
