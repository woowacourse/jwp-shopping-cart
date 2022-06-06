package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.dto.ErrorResponse;

public class ShoppingCartException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus httpStatus;

    public ShoppingCartException(final String errorCode, final String message, final HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorResponse toErrorResponse() {
        return new ErrorResponse(errorCode, getMessage());
    }
}
