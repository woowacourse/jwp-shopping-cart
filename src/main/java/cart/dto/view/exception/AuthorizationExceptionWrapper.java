package cart.dto.view.exception;

public class AuthorizationExceptionWrapper {

    private final String message;

    private AuthorizationExceptionWrapper(String message) {
        this.message = message;
    }

    public static AuthorizationExceptionWrapper of(String message) {
        return new AuthorizationExceptionWrapper(message);
    }

    public String getMessage() {
        return message;
    }
}
