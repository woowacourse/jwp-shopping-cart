package woowacourse.shoppingcart.exception;

import woowacourse.exception.BadRequestException;

public class InvalidCartItemException extends BadRequestException {
    public InvalidCartItemException() {
        this("유효하지 않은 장바구니입니다.");
    }

    public InvalidCartItemException(final String msg) {
        super(msg);
    }
}
