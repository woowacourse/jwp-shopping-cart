package cart.dto.response;

import cart.exception.ErrorCode;

public class ErrorResponse {
    private String code;
    private String message;
    private String detail;

    public ErrorResponse() {
    }

    private ErrorResponse(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errorCode.getDetail());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }
}
