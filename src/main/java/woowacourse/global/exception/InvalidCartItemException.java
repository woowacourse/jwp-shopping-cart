package woowacourse.global.exception;

public class InvalidCartItemException extends ShoppingCartException {

    public InvalidCartItemException(String message) {
        super(message);
    }
}
