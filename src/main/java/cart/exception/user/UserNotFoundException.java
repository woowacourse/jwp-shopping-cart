package cart.exception.user;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {

    public UserNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
