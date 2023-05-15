package cart.global.exception.auth;

import cart.global.exception.common.CartException;
import cart.global.exception.common.ExceptionStatus;

public class InvalidAuthorizationException extends CartException {
    public InvalidAuthorizationException() {
        super(ExceptionStatus.INVALID_AUTHORIZATION);
    }
}
