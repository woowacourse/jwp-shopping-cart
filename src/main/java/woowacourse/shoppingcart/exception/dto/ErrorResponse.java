package woowacourse.shoppingcart.exception.dto;

public class ErrorResponse {

    private final int errorCode;
    private final String message;

    public ErrorResponse(final int errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return String.valueOf(errorCode);
    }

    public String getMessage() {
        return message;
    }
}
