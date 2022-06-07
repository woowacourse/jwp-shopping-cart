package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class InvalidQuantityException extends BadRequestException {

    public InvalidQuantityException() {
        super("999", "수량이 유효하지 않습니다.");
    }
}
