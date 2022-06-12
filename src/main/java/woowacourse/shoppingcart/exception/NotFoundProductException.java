package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class NotFoundProductException extends CartException {

    public NotFoundProductException() {
        super("존재하지 않는 상품 ID입니다.", HttpStatus.NOT_FOUND);
    }
}
