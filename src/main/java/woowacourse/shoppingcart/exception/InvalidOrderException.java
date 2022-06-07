package woowacourse.shoppingcart.exception;

public class InvalidOrderException extends OrderException {

    public InvalidOrderException() {
        super("잘못된 주문 정보입니다.");
    }
}
