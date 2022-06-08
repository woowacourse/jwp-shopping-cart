package woowacourse.shoppingcart.exception.customer;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class InvalidPasswordException extends ShoppingCartBadRequestException {

    public static final int ERROR_CODE = 1000;
    public static final String MESSAGE = "잘못된 비밀번호 형식입니다.";

    public InvalidPasswordException() {
        super(ERROR_CODE, MESSAGE);
    }
}
