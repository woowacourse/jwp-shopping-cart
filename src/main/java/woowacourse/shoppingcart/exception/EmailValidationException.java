package woowacourse.shoppingcart.exception;

public final class EmailValidationException extends ValidationException {

    public EmailValidationException(final String message) {
        super(message, "email");
    }
}
