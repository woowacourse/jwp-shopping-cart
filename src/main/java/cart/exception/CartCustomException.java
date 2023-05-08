package cart.exception;

import org.springframework.http.HttpStatus;

public abstract class CartCustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CartCustomException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
