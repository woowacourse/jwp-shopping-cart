package woowacourse.shoppingcart.exception.domain;

public class CartItemNotFoundException extends NotFoundException {

    public CartItemNotFoundException() {
        this("유효하지 않은 장바구니입니다.");
    }

    public CartItemNotFoundException(final String msg) {
        super(msg);
    }
}
