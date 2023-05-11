package cart.exception;

public class AuthorizationException extends RuntimeException {
    private String message;

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
