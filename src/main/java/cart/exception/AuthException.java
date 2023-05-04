package cart.exception;

import org.springframework.http.HttpStatus;

public abstract class AuthException extends RuntimeException{

    public AuthException(final String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

}
