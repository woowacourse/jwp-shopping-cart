package cart.global.exception.auth;

import cart.global.exception.CartException;
import cart.global.exception.ExceptionStatus;

public class InvalidAuthorizationException extends CartException {
    public InvalidAuthorizationException() {
        super(ExceptionStatus.INVALID_AUTHORIZATION.getMessage());
    }
}
