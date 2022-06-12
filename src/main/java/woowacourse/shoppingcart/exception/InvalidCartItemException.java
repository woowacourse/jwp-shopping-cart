package woowacourse.shoppingcart.exception;

public class InvalidCartItemException extends RuntimeException {

    public InvalidCartItemException(final String msg) {
        super(msg);
    }
}
