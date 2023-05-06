package cart.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public HttpException(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
