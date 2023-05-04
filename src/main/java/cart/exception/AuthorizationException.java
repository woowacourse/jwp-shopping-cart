package cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {
    private String message;

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
