package woowacourse.shoppingcart.exception;

public class InvalidQuantityException extends IllegalArgumentException {

    public InvalidQuantityException() {
        super("수량은 양수여야 합니다.");
    }
}
