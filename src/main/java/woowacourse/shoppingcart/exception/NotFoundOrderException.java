package woowacourse.shoppingcart.exception;

public class NotFoundOrderException extends NotFoundException {

    private static final String MESSAGE = "유효하지 않은 주문입니다.";

    public NotFoundOrderException() {
        super(MESSAGE);
    }

}
