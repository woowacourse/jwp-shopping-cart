package cart.exception.customexceptions;

public class AdminAccessException extends RuntimeException {

    public AdminAccessException(final String message) {
        super(message);
    }
}
