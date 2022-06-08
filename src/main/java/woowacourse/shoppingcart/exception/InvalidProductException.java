package woowacourse.shoppingcart.exception;

import woowacourse.common.exception.NotFoundException;

public class InvalidProductException extends NotFoundException {

    public InvalidProductException() {
        this("존재하지 않는 상품입니다.");
    }

    public InvalidProductException(final String msg) {
        super(msg);
    }
}
