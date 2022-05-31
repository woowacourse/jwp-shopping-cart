package woowacourse.shoppingcart.exception.notfound;

import woowacourse.shoppingcart.exception.badrequest.NotFoundException;

public class InvalidCustomerException extends NotFoundException {

    public InvalidCustomerException() {
        super("1004", "존재하지 않는 유저입니다.");
    }
}
