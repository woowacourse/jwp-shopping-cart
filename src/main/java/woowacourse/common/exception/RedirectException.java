package woowacourse.common.exception;

import org.springframework.http.HttpStatus;

public class RedirectException extends RuntimeException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public RedirectException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return STATUS;
    }
}
