package woowacourse.exception;

public class CartNotFoundException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "장바구니에 없는 상품입니다";

    public CartNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
