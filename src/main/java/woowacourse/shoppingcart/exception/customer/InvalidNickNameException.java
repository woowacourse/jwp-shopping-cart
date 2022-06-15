package woowacourse.shoppingcart.exception.customer;

import woowacourse.shoppingcart.exception.ShoppingCartBadRequestException;

public class InvalidNickNameException extends ShoppingCartBadRequestException {

    public static final int ERROR_CODE = 1000;
    public static final String MESSAGE = "잘못된 닉네임 형식입니다.";

    public InvalidNickNameException() {
        super(ERROR_CODE, MESSAGE);
    }
}
