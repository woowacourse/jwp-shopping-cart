package woowacourse.shoppingcart.exception;

import woowacourse.common.exception.BadRequestException;

public class DuplicatedCustomerException extends BadRequestException {

    public DuplicatedCustomerException() {
        super("이미 존재하는 계정입니다.");
    }
}
