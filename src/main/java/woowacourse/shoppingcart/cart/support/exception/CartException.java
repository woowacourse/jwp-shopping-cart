package woowacourse.shoppingcart.cart.support.exception;

import woowacourse.support.exception.ShoppingCartException;

public class CartException extends ShoppingCartException {

    public CartException(final CartExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
