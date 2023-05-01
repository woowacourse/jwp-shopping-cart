package cart.config.auth;

public class AuthLoginException extends RuntimeException {
    public AuthLoginException(final String message) {
        super(message);
    }
}
