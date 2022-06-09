package woowacourse.shoppingcart.application.exception;

public class InvalidQuantityException extends ValidatedException {
    public InvalidQuantityException() {
        this("수량은 음수일 수 없습니다.");
    }

    public InvalidQuantityException(final String msg) {
        super(msg);
    }
}
