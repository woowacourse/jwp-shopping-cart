package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class IllegalFormException extends ShoppingCartException {

    private static final String ERROR_MESSAGE = "의 입력 형식에 맞지 않습니다.";

    public IllegalFormException(final String form) {
        super(form + ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
