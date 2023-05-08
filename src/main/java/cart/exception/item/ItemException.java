package cart.exception.item;

import org.springframework.http.HttpStatus;

public abstract class ItemException extends IllegalArgumentException {

    private final HttpStatus httpStatus;

    ItemException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
