package woowacourse.shoppingcart.exception.customer;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class DuplicateCustomerBadRequestException extends ShoppingCartBadRequestException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";
    private static final int ERROR_CODE = 1001;

    public DuplicateCustomerBadRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
