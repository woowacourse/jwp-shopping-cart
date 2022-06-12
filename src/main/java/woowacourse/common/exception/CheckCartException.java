package woowacourse.common.exception;

import org.springframework.http.HttpStatus;

public class CheckCartException extends RuntimeException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CheckCartException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return STATUS;
    }
}
