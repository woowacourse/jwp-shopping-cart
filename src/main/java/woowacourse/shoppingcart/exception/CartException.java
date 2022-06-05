package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public abstract class CartException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CartException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
