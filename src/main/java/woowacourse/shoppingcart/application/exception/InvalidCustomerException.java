package woowacourse.shoppingcart.application.exception;

import org.springframework.http.HttpStatus;

public final class InvalidCustomerException extends ShoppingCartException {
    public InvalidCustomerException() {
        this("존재하지 않는 유저입니다.");
    }

    public InvalidCustomerException(final String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
