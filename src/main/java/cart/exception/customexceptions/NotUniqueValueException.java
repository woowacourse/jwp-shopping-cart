package cart.exception.customexceptions;

public class NotUniqueValueException extends RuntimeException {

    public NotUniqueValueException(final String message) {
        super(message);
    }
}
