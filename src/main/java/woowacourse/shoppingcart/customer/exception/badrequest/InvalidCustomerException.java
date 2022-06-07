package woowacourse.shoppingcart.customer.exception.badrequest;

import woowacourse.shoppingcart.exception.BadRequestException;

public class InvalidCustomerException extends BadRequestException {

    public InvalidCustomerException() {
        super("1004", "존재하지 않는 유저입니다.");
    }
}
