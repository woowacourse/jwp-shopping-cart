package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends RuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    private final HttpStatus httpStatus;

    public InvalidInputException(final String message) {
        super(message);
        this.httpStatus = HTTP_STATUS;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
