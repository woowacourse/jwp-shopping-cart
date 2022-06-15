package woowacourse.exception.notfound;

import woowacourse.exception.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    private static final String DEFAULT_MESSAGE = "주문 조회에 실패했습니다";

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
