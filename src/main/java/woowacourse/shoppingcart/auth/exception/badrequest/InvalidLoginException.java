package woowacourse.shoppingcart.auth.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class InvalidLoginException extends BadRequestException {

    public InvalidLoginException() {
        super(ErrorCode.INVALID_LOGIN_REQUEST, "로그인에 실패했습니다.");
    }
}
