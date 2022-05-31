package woowacourse.shoppingcart.exception;

public class UserNotFoundException extends LoginException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
