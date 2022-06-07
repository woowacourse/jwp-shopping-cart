package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class DuplicateEmailException extends BadRequestException {

    public DuplicateEmailException() {
        super("1001", "이메일이 중복입니다.");
    }
}
