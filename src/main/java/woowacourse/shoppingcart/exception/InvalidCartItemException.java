package woowacourse.shoppingcart.exception;

public class InvalidCartItemException extends RuntimeException {

    public InvalidCartItemException() {
        super("유효하지 않은 장바구니입니다.");
    }
}
