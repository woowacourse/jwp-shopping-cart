package woowacourse.global.exception;

public class InvalidCartItemException extends ShoppingCartException {

    public InvalidCartItemException(final String message) {
        super(message);
    }
}
