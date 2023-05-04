package cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserAuthentificationException extends RuntimeException {
    public UserAuthentificationException(String message) {
        super(message);
    }
}
