package woowacourse.shoppingcart.application.exception;

public abstract class ValidatedException extends RuntimeException {

    public ValidatedException(final String message) {
        super(message);
    }
}
