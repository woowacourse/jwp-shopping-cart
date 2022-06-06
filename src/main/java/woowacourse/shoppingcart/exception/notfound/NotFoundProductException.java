package woowacourse.shoppingcart.exception.notfound;

public class NotFoundProductException extends NotFoundException {

    private static final String MESSAGE = "상품을 찾을 수 없습니다.";
    private static final int ERROR_CODE = 2000;

    public NotFoundProductException() {
        super(ERROR_CODE, MESSAGE);
    }
}
