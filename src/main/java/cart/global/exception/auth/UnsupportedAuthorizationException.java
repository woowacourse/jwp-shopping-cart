package cart.global.exception.auth;

import cart.global.exception.common.CartException;
import cart.global.exception.common.ExceptionStatus;

public class UnsupportedAuthorizationException extends CartException {
    public UnsupportedAuthorizationException() {
        super(ExceptionStatus.UNSUPPORTED_AUTHORIZATION_HEADER);
    }
}
