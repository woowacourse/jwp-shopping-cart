package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class AlreadyInCartException extends ClientRuntimeException {

    private static final String MESSAGE = "이미 장바구니에 담겨져 있습니다.";

    public AlreadyInCartException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
