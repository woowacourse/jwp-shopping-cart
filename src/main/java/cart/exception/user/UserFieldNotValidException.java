package cart.exception.user;

import org.springframework.http.HttpStatus;

public class UserFieldNotValidException extends UserException {

    public UserFieldNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
