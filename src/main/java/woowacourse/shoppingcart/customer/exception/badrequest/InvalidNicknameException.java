package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class InvalidNicknameException extends BadRequestException {

    public InvalidNicknameException() {
        super(ErrorCode.INVALID_REQUEST_FORM, "닉네임이 유효하지 않습니다.");
    }
}
