package cart.domain.exception;

public class AuthorizationNotIncludedException extends RuntimeException {

    public AuthorizationNotIncludedException() {
    }

    public AuthorizationNotIncludedException(String message) {
        super(message);
    }
}
