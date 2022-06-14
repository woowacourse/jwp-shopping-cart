package woowacourse.shoppingcart.order.exception.notfound;

import woowacourse.shoppingcart.exception.ErrorCode;
import woowacourse.shoppingcart.exception.NotFoundException;

public class NotFoundOrderException extends NotFoundException {

    public NotFoundOrderException() {
        super(ErrorCode.GENERAL_NOT_FOUND, "주문이 존재하지 않습니다.");
    }
}
