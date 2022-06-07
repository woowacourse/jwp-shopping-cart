package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ShoppingCartException {

    public NotFoundException(final ErrorCode errorCode, final String message) {
        super(errorCode, message, HttpStatus.NOT_FOUND);
    }
}
