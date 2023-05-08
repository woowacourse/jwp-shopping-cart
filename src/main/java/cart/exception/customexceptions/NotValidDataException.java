package cart.exception.customexceptions;

public class NotValidDataException extends RuntimeException {

    public NotValidDataException(final String message) {
        super(message);
    }
}
