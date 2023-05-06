package cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "입력한 정보의 회원은 존재하지 않습니다.";

    public AuthorizationException() {
        super(DEFAULT_MESSAGE);
    }

    public AuthorizationException(final String message) {
        super(message);
    }
}
