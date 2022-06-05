package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidProductException extends ClientRuntimeException {

    private static final String MESSAGE = "올바르지 않은 사용자 이름이거나 상품 아이디 입니다.";

    public InvalidProductException(final String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }

    public InvalidProductException() {
        this(MESSAGE);
    }
}
