package cart.web.exception;

public class IllegalAuthorizationPrefixException extends RuntimeException {
    public IllegalAuthorizationPrefixException(final String message) {
        super(message);
    }
}
