package woowacourse.shoppingcart.ui.dto;

public class ErrorResponse {

    private String message;

    private ErrorResponse() {
    }

    private ErrorResponse(final String message) {
        this.message = message;
    }

    public static ErrorResponse from(final String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse from(final Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public String getMessage() {
        return message;
    }
}
