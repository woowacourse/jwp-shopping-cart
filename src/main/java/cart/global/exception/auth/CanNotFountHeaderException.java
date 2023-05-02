package cart.global.exception.auth;

import cart.global.exception.common.BusinessException;
import cart.global.exception.common.ExceptionStatus;

public class CanNotFountHeaderException extends BusinessException {

    public CanNotFountHeaderException() {
        super(ExceptionStatus.CAN_NOT_FOUNT_HEADER_EXCEPTION);
    }
}
