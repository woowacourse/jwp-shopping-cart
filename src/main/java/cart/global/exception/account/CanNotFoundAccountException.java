package cart.global.exception.account;

import cart.global.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class CanNotFoundAccountException extends BusinessException {

    public CanNotFoundAccountException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
