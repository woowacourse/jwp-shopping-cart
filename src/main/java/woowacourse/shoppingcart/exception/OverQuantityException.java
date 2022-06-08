package woowacourse.shoppingcart.exception;

public class OverQuantityException extends RuntimeException {

    public OverQuantityException() {
        super("재고가 부족합니다.");
    }
}
