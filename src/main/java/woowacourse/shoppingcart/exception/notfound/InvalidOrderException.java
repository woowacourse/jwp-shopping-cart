package woowacourse.shoppingcart.exception.notfound;

import woowacourse.shoppingcart.exception.badrequest.BadRequestException;

public class InvalidOrderException extends BadRequestException {

    public InvalidOrderException(final String message) {
        super("1004", message);
    }

    public InvalidOrderException() {
        this("유효하지 않은 주문입니다.");
    }
}
