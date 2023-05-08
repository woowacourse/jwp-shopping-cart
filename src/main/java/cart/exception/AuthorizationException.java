package cart.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends CartCustomException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "로그인을 먼저 해주십시오";

    public AuthorizationException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
