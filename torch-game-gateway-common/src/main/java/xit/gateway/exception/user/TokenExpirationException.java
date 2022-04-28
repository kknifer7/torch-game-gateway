package xit.gateway.exception.user;

public class TokenExpirationException extends RuntimeException{
    public TokenExpirationException(){
        super();
    }

    public TokenExpirationException(String detailMessage){
        super(detailMessage);
    }
}
