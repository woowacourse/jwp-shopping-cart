package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidOrderException extends ClientRuntimeException {

    private static final String MESSAGE = "유효하지 않은 주문입니다.";

    public InvalidOrderException() {
        this(MESSAGE);
    }

    public InvalidOrderException(final String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
