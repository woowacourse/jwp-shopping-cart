package cart.exception.auth;

import org.springframework.http.HttpStatus;

public class NotValidTokenFormatException extends AuthException {

    public NotValidTokenFormatException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
