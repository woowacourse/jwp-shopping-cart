package woowacourse.shoppingcart.exception;

public class UsernameValidationException extends ValidationException {

    public UsernameValidationException(final String message) {
        super(message, "username");
    }
}
