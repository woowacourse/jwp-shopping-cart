package woowacourse.shoppingcart.exception;

public class IllegalCartItemException extends RuntimeException {
    public IllegalCartItemException(final String msg) {
        super(msg);
    }
}
