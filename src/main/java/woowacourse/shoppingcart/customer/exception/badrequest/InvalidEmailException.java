package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class InvalidEmailException extends BadRequestException {

    public InvalidEmailException() {
        super(ErrorCode.INVALID_REQUEST_FORM, "이메일이 유효하지 않습니다.");
    }
}
