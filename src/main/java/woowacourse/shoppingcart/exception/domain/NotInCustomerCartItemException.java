package woowacourse.shoppingcart.exception.domain;

import woowacourse.shoppingcart.exception.ShoppingCartException;

public class NotInCustomerCartItemException extends ShoppingCartException {

    public NotInCustomerCartItemException() {
        this("장바구니 아이템이 없습니다.");
    }

    public NotInCustomerCartItemException(final String msg) {
        super(msg);
    }
}
