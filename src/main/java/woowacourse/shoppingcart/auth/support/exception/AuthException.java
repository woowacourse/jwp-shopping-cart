package woowacourse.shoppingcart.auth.support.exception;

import woowacourse.support.exception.ShoppingCartException;

public class AuthException extends ShoppingCartException {

    public AuthException(final AuthExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
