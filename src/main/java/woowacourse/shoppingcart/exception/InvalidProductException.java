package woowacourse.shoppingcart.exception;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(final String msg) {
        super(msg);
    }
}
