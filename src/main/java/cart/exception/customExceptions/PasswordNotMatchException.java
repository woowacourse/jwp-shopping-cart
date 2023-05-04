package cart.exception.customExceptions;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException(final String message) {
        super(message);
    }
}
