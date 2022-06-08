package woowacourse.auth.dto;

public class ExceptionResponse {

    private int errorCode;
    private String message;

    public ExceptionResponse() {
    }

    public ExceptionResponse(int errorCode, String message) {
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
