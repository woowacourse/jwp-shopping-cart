package cart.exception.auth;

import org.springframework.http.HttpStatus;

public class NotValidTokenInfoException extends AuthException {

    public NotValidTokenInfoException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
