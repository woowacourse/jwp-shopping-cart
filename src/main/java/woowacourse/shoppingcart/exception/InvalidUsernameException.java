package woowacourse.shoppingcart.exception;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException() {
    }

    public InvalidUsernameException(String message) {
        super(message);
    }
}
