package woowacourse.global.exception;

public abstract class ShoppingCartException extends RuntimeException {

    public ShoppingCartException(final String message) {
        super(message);
    }
}
