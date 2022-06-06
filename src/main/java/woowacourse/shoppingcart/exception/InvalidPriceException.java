package woowacourse.shoppingcart.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException() {
        this("유효하지 않은 가격입니다.");
    }

    public InvalidPriceException(final String msg) {
        super(msg);
    }
}
