package cart.global.exception.auth;

import cart.global.exception.common.BusinessException;
import cart.global.exception.common.ExceptionStatus;

public class CanNotFoundHeaderException extends BusinessException {

    public CanNotFoundHeaderException() {
        super(ExceptionStatus.CAN_NOT_FOUNT_HEADER_EXCEPTION);
    }
}
