package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class InvalidOrderException extends CartException {

    public InvalidOrderException() {
        super("유효하지 않은 주문입니다.", HttpStatus.BAD_REQUEST);
    }
}
