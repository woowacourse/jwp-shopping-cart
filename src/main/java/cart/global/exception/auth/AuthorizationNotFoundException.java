package cart.global.exception.auth;

import cart.global.exception.common.CartException;
import cart.global.exception.common.ExceptionStatus;

public class AuthorizationNotFoundException extends CartException {

    public AuthorizationNotFoundException() {
        super(ExceptionStatus.NOT_FOUND_AUTHORIZATION_HEADER);
    }
}
