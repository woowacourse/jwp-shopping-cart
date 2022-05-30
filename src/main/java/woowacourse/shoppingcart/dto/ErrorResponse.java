package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.ui.ControllerAdvice.ErrorCode;

public class ErrorResponse {
    private int errorCode;
    private String message;


    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }
    public ErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
