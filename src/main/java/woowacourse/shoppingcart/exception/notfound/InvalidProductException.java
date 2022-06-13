package woowacourse.shoppingcart.exception.notfound;

public class InvalidProductException extends NotFoundException {

    private static final String MESSAGE = "올바르지 않은 사용자 아이디이거나 상품 아이디 입니다.";

    public InvalidProductException() {
        this(MESSAGE);
    }

    public InvalidProductException(final String msg) {
        super(msg);
    }
}
