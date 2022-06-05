package woowacourse.shoppingcart.exception;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(final String msg) {
        super(msg);
    }
}
