package cart.global.exception.account;

import cart.global.exception.common.BusinessException;
import cart.global.exception.common.ExceptionStatus;

public class CanNotFoundAccountException extends BusinessException {

    public CanNotFoundAccountException() {
        super(ExceptionStatus.CAN_NOT_FOUND_ACCOUNT_EXCEPTION);
    }
}
