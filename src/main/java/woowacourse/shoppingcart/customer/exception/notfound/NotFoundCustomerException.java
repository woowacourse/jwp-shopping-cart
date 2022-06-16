package woowacourse.shoppingcart.customer.exception.notfound;

import woowacourse.shoppingcart.exception.ErrorCode;
import woowacourse.shoppingcart.exception.NotFoundException;

public class NotFoundCustomerException extends NotFoundException {

    public NotFoundCustomerException() {
        super(ErrorCode.GENERAL_NOT_FOUND, "회원이 존재하지 않습니다.");
    }
}
