package woowacourse.exception;

import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.ExceptionResponse;

public class CustomException extends RuntimeException {

    private int errorCode;
    private String message;
    private HttpStatus httpStatus;

    public CustomException(Error error) {
        this.errorCode = error.getErrorCode();
        this.message = error.getMessage();
        this.httpStatus = error.getHttpStatus();
    }

    public ExceptionResponse toExceptionResponse() {
        return new ExceptionResponse(errorCode, message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
