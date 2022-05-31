package woowacourse.shoppingcart.exception.notfound;

import woowacourse.shoppingcart.exception.badrequest.NotFoundException;

public class InvalidCartItemException extends NotFoundException {

    public InvalidCartItemException() {
        super("1004", "유효하지 않은 장바구니입니다.");
    }
}
