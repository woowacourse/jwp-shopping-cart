package cart.config.auth;

public class AuthenticationFailException extends RuntimeException {

    public AuthenticationFailException(String message) {
        super(message);
    }
}
