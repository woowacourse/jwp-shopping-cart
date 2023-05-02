package cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {

    private static final String MESSAGE = "로그인을 먼저 해주십시오";

    public AuthorizationException() {
        super(MESSAGE);
    }
}
