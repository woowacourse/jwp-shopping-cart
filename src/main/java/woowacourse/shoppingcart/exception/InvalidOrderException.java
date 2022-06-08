package woowacourse.shoppingcart.exception;

public class InvalidOrderException extends RuntimeException {

    private static final String MESSAGE = "유효하지 않은 주문입니다.";

    public InvalidOrderException() {
        super(MESSAGE);
    }

}
