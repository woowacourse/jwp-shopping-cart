package cart.global.exception.auth;

import cart.global.exception.CartException;
import cart.global.exception.ExceptionStatus;

public class AuthorizationNotFoundException extends CartException {

    public AuthorizationNotFoundException() {
        super(ExceptionStatus.NOT_FOUND_AUTHORIZATION_HEADER.getMessage());
    }
}
