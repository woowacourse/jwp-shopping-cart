package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class InvalidOrderException extends ShoppingCartException {

    public InvalidOrderException() {
        this("유효하지 않은 주문입니다.");
    }

    public InvalidOrderException(final String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
