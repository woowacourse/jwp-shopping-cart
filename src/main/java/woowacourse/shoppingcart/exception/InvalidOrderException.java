package woowacourse.shoppingcart.exception;

import woowacourse.common.exception.NotFoundException;

public class InvalidOrderException extends NotFoundException {

    public InvalidOrderException() {
        this("존재하지 않은 주문입니다.");
    }

    public InvalidOrderException(final String msg) {
        super(msg);
    }
}
