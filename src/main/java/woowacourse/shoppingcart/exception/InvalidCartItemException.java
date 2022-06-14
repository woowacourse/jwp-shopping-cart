package woowacourse.shoppingcart.exception;

public class InvalidCartItemException extends BadRequestException {

    private static final String MESSAGE = "장바구니에 상품이 존재하지 않습니다.";
    private static final int ERROR_CODE = 1102;

    public InvalidCartItemException() {
        super(ERROR_CODE, MESSAGE);
    }
}
