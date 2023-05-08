package cart.exception.user;

import org.springframework.http.HttpStatus;

public class SignInFailureException extends UserException {

    public SignInFailureException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
