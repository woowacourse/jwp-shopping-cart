package woowacourse.shoppingcart.exception.notfound;

import woowacourse.shoppingcart.exception.badrequest.BadRequestException;

public class InvalidCartItemException extends BadRequestException {

    public InvalidCartItemException() {
        super("1004", "유효하지 않은 장바구니입니다.");
    }
}
