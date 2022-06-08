package woowacourse.shoppingcart.exception;

import woowacourse.common.exception.NotFoundException;

public class InvalidCartItemException extends NotFoundException {

    public InvalidCartItemException() {
        this("존재하지 않은 장바구니입니다.");
    }

    public InvalidCartItemException(final String msg) {
        super(msg);
    }
}
