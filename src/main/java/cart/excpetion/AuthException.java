package cart.excpetion;

public class AuthException extends RuntimeException {

    public AuthException(final String message) {
        super(message);
    }
}
