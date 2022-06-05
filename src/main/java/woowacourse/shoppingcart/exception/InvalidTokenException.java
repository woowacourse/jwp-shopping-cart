package woowacourse.shoppingcart.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(final String msg) {
        super(msg);
    }
}
