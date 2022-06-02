package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public abstract class ShoppingCartException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ShoppingCartException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public final HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
