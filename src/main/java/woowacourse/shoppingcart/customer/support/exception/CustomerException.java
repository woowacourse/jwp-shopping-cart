package woowacourse.shoppingcart.customer.support.exception;

import woowacourse.support.exception.ShoppingCartException;

public class CustomerException extends ShoppingCartException {

    public CustomerException(final CustomerExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
