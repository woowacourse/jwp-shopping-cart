package woowacourse.shoppingcart.exception;

public class InvalidPriceException extends IllegalArgumentException {
    public InvalidPriceException() {
        super("금액은 음수일 수 없습니다.");
    }
}
