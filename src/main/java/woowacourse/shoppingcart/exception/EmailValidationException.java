package woowacourse.shoppingcart.exception;

public class EmailValidationException extends ValidationException {

    public EmailValidationException(final String message) {
        super(message, "email");
    }
}
