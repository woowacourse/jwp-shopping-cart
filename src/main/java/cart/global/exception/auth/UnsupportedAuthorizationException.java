package cart.global.exception.auth;

import cart.global.exception.CartException;
import cart.global.exception.ExceptionStatus;

public class UnsupportedAuthorizationException extends CartException {
    public UnsupportedAuthorizationException() {
        super(ExceptionStatus.UNSUPPORTED_AUTHORIZATION_HEADER.getMessage());
    }
}
