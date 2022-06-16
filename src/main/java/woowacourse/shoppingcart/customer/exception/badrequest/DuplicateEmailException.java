package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;
import woowacourse.shoppingcart.exception.ErrorCode;

public class DuplicateEmailException extends BadRequestException {

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL, "이메일이 중복입니다.");
    }
}
