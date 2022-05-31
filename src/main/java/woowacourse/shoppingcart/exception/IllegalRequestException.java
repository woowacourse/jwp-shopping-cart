package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class IllegalRequestException extends ShoppingCartException {

    public IllegalRequestException(final String errorCode, final String message) {
        super(errorCode, message, HttpStatus.BAD_REQUEST);
    }
}
