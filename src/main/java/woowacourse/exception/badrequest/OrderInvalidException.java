package woowacourse.exception.badrequest;

import woowacourse.exception.BadRequestException;

public class OrderInvalidException extends BadRequestException {
    private static final String DEFAULT_MESSAGE = "주문하고자 하는 상품이 장바구니에 없습니다";

    public OrderInvalidException() {
        super(DEFAULT_MESSAGE);
    }
}
