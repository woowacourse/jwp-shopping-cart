package woowacourse.shoppingcart.application.exception;

import org.springframework.http.HttpStatus;

public abstract class ShoppingCartException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ShoppingCartException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public final HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
