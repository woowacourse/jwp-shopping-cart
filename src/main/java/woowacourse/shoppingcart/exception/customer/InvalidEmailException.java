package woowacourse.shoppingcart.exception.customer;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class InvalidEmailException extends ShoppingCartBadRequestException {

    public static final int ERROR_CODE = 1000;
    public static final String MESSAGE = "잘못된 이메일 형식입니다.";

    public InvalidEmailException() {
        super(ERROR_CODE, MESSAGE);
    }
}