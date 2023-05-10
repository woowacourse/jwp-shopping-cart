package cart.global.exception.auth;

import cart.global.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class CanNotFoundHeaderException extends BusinessException {

    public CanNotFoundHeaderException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
