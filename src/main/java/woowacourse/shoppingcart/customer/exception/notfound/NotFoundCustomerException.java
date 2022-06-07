package woowacourse.shoppingcart.customer.exception.notfound;

import woowacourse.shoppingcart.exception.notfound.NotFoundException;

public class NotFoundCustomerException extends NotFoundException {

    public NotFoundCustomerException() {
        super("2000", "회원이 존재하지 않습니다.");
    }
}
