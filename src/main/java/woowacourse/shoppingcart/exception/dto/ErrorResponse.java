package woowacourse.shoppingcart.exception.dto;

public class ErrorResponse {

    private final String errorCode;
    private final String message;

    public ErrorResponse(final String errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
