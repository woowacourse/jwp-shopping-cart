package cart.exception.cart;

import org.springframework.http.HttpStatus;

public abstract class CartException extends IllegalArgumentException {

    private final HttpStatus httpStatus;

    CartException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
