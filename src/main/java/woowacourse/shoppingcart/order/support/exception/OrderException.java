package woowacourse.shoppingcart.order.support.exception;

import woowacourse.support.exception.ShoppingCartException;

public class OrderException extends ShoppingCartException {

    public OrderException(final OrderExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
