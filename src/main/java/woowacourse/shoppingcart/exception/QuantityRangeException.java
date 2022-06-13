package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class QuantityRangeException extends ShoppingCartException {

    public QuantityRangeException() {
        this("수량은 0~99개까지 가능합니다.");
    }

    public QuantityRangeException(final String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }
}
