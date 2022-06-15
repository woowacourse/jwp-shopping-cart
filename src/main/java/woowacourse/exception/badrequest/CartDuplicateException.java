package woowacourse.exception.badrequest;

import woowacourse.exception.BadRequestException;

public class CartDuplicateException extends BadRequestException {
    private static final String DEFAULT_MESSAGE = "이미 장바구니에 존재하는 상품입니다";

    public CartDuplicateException() {
        super(DEFAULT_MESSAGE);
    }
}
