package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.dto.ErrorResponse;

public class ShoppingCartException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    public ShoppingCartException(final ErrorCode errorCode, final String message, final HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorResponse toErrorResponse() {
        return new ErrorResponse(errorCode.getValue(), message);
    }
}
