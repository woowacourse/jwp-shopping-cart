package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class NotFoundOrdersException extends CartException {

    public NotFoundOrdersException() {
        super("주문 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
