package cart.exception.customExceptions;

public class NotUniqueValueException extends RuntimeException {

    public NotUniqueValueException(final String message) {
        super(message);
    }
}
