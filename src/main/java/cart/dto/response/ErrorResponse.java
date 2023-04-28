package cart.dto.response;

import cart.ErrorCode;

public class ErrorResponse {
    private final int statusCode;
    private final String errorMessage;

    public ErrorResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.errorMessage = errorCode.getMessage();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
