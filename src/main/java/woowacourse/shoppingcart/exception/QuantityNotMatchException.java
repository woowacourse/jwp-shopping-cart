package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class QuantityNotMatchException extends ShoppingCartException {

    public QuantityNotMatchException() {
        this("장바구니에 저장되어있는 상품의 수량과 주문하려는 수량이 다릅니다.");
    }

    public QuantityNotMatchException(final String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }
}
