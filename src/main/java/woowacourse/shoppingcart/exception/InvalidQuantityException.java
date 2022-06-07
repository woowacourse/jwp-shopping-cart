package woowacourse.shoppingcart.exception;

public class InvalidQuantityException extends IllegalArgumentException {
    public InvalidQuantityException() {
        super("존재할 수 없는 수량입니다.");
    }
}
