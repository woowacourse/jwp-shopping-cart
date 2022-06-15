package woowacourse.shoppingcart.exception;

import woowacourse.exception.BadRequestException;

public class InvalidOrderException extends BadRequestException {
    public InvalidOrderException() {
        this("유효하지 않은 주문입니다.");
    }

    public InvalidOrderException(final String msg) {
        super(msg);
    }
}
