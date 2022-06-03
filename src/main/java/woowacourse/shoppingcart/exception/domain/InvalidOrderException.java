package woowacourse.shoppingcart.exception.domain;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class InvalidOrderException extends ShoppingCartException {

    public InvalidOrderException() {
        this("유효하지 않은 주문입니다.");
    }

    public InvalidOrderException(final String msg) {
        super(msg);
    }
}
