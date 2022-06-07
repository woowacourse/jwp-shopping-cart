package woowacourse.exception;

public class NotFoundException extends ShoppingCartException {
    public NotFoundException(final String message) {
        super(message);
    }
}
