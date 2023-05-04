package cart.exception.customExceptions;

public class AdminAccessException extends RuntimeException {

    public AdminAccessException(final String message) {
        super(message);
    }
}
