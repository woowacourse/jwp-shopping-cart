package woowacourse.exception.notfound;

import woowacourse.exception.NotFoundException;

public class CartNotFoundException extends NotFoundException {
    private static final String DEFAULT_MESSAGE = "장바구니에 없는 상품입니다";

    public CartNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
