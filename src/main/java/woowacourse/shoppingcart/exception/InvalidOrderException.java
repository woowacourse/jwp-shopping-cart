package woowacourse.shoppingcart.exception;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException() {
        super("유효하지 않은 주문입니다.");
    }
}
