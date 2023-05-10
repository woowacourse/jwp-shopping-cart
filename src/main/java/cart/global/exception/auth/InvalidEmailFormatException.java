package cart.global.exception.auth;

import cart.global.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidEmailFormatException extends BusinessException {

    public InvalidEmailFormatException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
