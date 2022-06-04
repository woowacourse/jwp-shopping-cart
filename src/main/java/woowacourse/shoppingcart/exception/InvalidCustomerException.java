package woowacourse.shoppingcart.exception;

import woowacourse.common.exception.NotFoundException;

public class InvalidCustomerException extends NotFoundException {

    public InvalidCustomerException() {
        this("존재하지 않는 사용자입니다.");
    }

    public InvalidCustomerException(final String msg) {
        super(msg);
    }
}
