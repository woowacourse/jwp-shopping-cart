package cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "존재하지 않는 회원입니다.";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
