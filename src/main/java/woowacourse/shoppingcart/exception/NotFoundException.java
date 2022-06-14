package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    private final HttpStatus httpStatus;

    public NotFoundException(final String message) {
        super(message);
        this.httpStatus = HTTP_STATUS;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
