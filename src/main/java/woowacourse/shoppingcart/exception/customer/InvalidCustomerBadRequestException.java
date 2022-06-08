package woowacourse.shoppingcart.exception.customer;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class InvalidCustomerBadRequestException extends ShoppingCartBadRequestException {

    private static final int ERROR_CODE = 1002;
    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public InvalidCustomerBadRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
