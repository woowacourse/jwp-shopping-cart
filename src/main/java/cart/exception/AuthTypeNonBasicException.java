package cart.exception;

import org.springframework.http.HttpStatus;

public class AuthTypeNonBasicException extends CartCustomException {

    private static final String MESSAGE = "Basic Auth방식으로 인증하지 않았습니다. Basic Auth로 인증해주십시오.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public AuthTypeNonBasicException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
