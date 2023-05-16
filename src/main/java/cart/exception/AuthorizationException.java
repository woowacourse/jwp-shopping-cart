package cart.exception;

public class AuthorizationException extends CustomException {

    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
