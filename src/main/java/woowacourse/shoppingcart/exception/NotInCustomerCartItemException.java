package woowacourse.shoppingcart.exception;

import woowacourse.exception.BadRequestException;

public class NotInCustomerCartItemException extends BadRequestException {
    public NotInCustomerCartItemException() {
        this("장바구니 아이템이 없습니다.");
    }

    public NotInCustomerCartItemException(final String msg) {
        super(msg);
    }
}
