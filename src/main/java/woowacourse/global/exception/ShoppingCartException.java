package woowacourse.global.exception;

public abstract class ShoppingCartException extends RuntimeException {

    public ShoppingCartException(String message) {
        super(message);
    }
}
