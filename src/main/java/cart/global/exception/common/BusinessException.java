package cart.global.exception.common;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public BusinessException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public HttpStatus getHttpStatus() {
        return exceptionStatus.getHttpStatus();
    }

    public int getStatus() {
        return exceptionStatus.getStatus();
    }
}
