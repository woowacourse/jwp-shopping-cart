package cart.exception.user;

import org.springframework.http.HttpStatus;

public class UserException extends IllegalArgumentException {

    private final HttpStatus httpStatus;

    UserException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
