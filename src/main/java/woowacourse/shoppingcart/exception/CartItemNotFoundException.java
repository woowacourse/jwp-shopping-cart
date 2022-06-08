package woowacourse.shoppingcart.exception;

public class CartItemNotFoundException extends NotFoundException {

    private static final String ERROR_MESSAGE = "장바구니 아이템이 없습니다.";

    public CartItemNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
