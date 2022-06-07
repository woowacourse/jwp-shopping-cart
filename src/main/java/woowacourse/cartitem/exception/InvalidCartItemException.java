package woowacourse.cartitem.exception;

import woowacourse.exception.BadRequestException;

public class InvalidCartItemException extends BadRequestException {

    public InvalidCartItemException() {
        this("장바구니 아이템을 찾을 수 없습니다.");
    }

    public InvalidCartItemException(final String msg) {
        super(msg);
    }
}
