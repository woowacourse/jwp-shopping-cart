package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ShoppingCartException {

    public BadRequestException(final ErrorCode errorCode, final String message) {
        super(errorCode, message, HttpStatus.BAD_REQUEST);
    }
}
