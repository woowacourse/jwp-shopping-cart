package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class InvalidPasswordException extends BadRequestException {

    public InvalidPasswordException() {
        super(ErrorCode.INVALID_REQUEST_FORM, "비밀번호가 유효하지 않습니다.");
    }
}
