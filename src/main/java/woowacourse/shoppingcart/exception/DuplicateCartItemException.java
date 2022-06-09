package woowacourse.shoppingcart.exception;

public class DuplicateCartItemException extends BadRequestException {

    private static final int ERROR_CODE = 1101;
    private static final String MESSAGE = "장바구니에 중복된 물품이 있습니다.";

    public DuplicateCartItemException() {
        super(ERROR_CODE, MESSAGE);
    }
}
