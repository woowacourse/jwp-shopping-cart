package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class AuthorizationFailException extends CartException {

    public AuthorizationFailException(final String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
