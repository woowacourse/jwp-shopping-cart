package cart.excpetion;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(final String message) {
        super(message);
    }
}
