package woowacourse.shoppingcart.exception;

public final class UsernameValidationException extends ValidationException {

    public UsernameValidationException(final String message) {
        super(message, "username");
    }
}
