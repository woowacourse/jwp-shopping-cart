package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class NotInCustomerCartItemException extends ClientRuntimeException {

    private static final String MESSAGE = "장바구니 아이템이 없습니다.";

    public NotInCustomerCartItemException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
