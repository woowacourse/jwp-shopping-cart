package woowacourse.shoppingcart.application.exception;

import org.springframework.http.HttpStatus;

public final class InvalidCartItemException extends ShoppingCartException {
    public InvalidCartItemException() {
        this("유효하지 않은 장바구니입니다.");
    }

    public InvalidCartItemException(final String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
