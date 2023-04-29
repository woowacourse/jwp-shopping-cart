package cart.exception.auth;

import org.springframework.http.HttpStatus;

public class NotSignInException extends AuthException {

    public NotSignInException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
