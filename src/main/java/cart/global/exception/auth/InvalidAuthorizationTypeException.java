package cart.global.exception.auth;

import cart.global.exception.common.BusinessException;
import cart.global.exception.common.ExceptionStatus;

public class InvalidAuthorizationTypeException extends BusinessException {

    public InvalidAuthorizationTypeException() {
        super(ExceptionStatus.INVALID_AUTHORIZATION_TYPE_EXCEPTION);
    }
}
