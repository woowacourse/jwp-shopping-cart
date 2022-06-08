package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class DuplicateCartItemException extends CartException {

    public DuplicateCartItemException() {
        super("이미 담겨있는 상품입니다.", HttpStatus.BAD_REQUEST);
    }
}
