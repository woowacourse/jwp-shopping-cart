package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class NotInCustomerCartItemException extends ShoppingCartException {

    public NotInCustomerCartItemException() {
        this("장바구니 아이템이 없습니다.");
    }

    public NotInCustomerCartItemException(final String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
