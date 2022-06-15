package woowacourse.support.dto;

public class ExceptionResponse {

    private Long errorCode;
    private String message;

    public ExceptionResponse() {
    }

    public ExceptionResponse(final Long errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
