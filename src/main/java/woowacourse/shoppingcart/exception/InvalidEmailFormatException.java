package woowacourse.shoppingcart.exception;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
    }

    public InvalidEmailFormatException(String message) {
        super(message);
    }
}
