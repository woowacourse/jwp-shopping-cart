package woowacourse.shoppingcart.application.exception;

import org.springframework.http.HttpStatus;

public final class InvalidProductException extends ShoppingCartException {
    public InvalidProductException() {
        this("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
    }

    public InvalidProductException(final String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
