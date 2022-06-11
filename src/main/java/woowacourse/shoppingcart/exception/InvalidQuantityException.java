package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class InvalidQuantityException extends CartException {

    public InvalidQuantityException() {
        super("상품의 재고는 음수일 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
