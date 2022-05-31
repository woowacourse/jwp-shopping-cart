package woowacourse.shoppingcart.exception.notfound;

import woowacourse.shoppingcart.exception.badrequest.NotFoundException;

public class NotInCustomerCartItemException extends NotFoundException {

    public NotInCustomerCartItemException() {
        super("1004", "장바구니 아이템이 없습니다.");
    }
}
