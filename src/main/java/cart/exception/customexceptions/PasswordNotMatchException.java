package cart.exception.customexceptions;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException(final String message) {
        super(message);
    }
}
