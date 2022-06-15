package woowacourse.exception;

public class BadRequestException extends ShoppingCartException {
    public BadRequestException(final String message) {
        super(message);
    }
}
