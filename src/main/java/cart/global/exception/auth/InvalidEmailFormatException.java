package cart.global.exception.auth;

public class InvalidEmailFormatException extends RuntimeException {

    public InvalidEmailFormatException(final String message) {
        super(message);
    }
}
