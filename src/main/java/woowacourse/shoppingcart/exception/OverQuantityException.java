package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class OverQuantityException extends CartException {

    public OverQuantityException() {
        super("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
    }
}
