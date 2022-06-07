package woowacourse.shoppingcart.exception.badrequest;

public class InvalidProductException extends BadRequestException {

    private static final int ERROR_CODE = 1000;
    private static final String MESSAGE = "올바르지 않은 사용자 이름이거나 상품 아이디 입니다.";

    public InvalidProductException() {
        super(ERROR_CODE, MESSAGE);
    }
}
