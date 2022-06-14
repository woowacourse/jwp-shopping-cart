package woowacourse.shoppingcart.exception;

public class InvalidPasswordFormatException extends RuntimeException {
    public InvalidPasswordFormatException() {
    }

    public InvalidPasswordFormatException(String message) {
        super(message);
    }
}
