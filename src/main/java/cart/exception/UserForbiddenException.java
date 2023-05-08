package cart.exception;

public class UserForbiddenException extends RuntimeException{

    public UserForbiddenException(final String message){
        super(message);
    }

}
