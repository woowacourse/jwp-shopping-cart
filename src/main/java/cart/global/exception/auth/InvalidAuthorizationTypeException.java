package cart.global.exception.auth;

import cart.global.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidAuthorizationTypeException extends BusinessException {

    public InvalidAuthorizationTypeException(final String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
