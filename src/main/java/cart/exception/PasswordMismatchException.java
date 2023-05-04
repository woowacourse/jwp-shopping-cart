package cart.exception;

public class PasswordMismatchException extends AuthException{

    public PasswordMismatchException(final String message) {
        super(message);
    }

}
