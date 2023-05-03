package cart.controller.auth;

public class AuthorizationException extends RuntimeException {

    private String message;

    public AuthorizationException(final String message) {
        this.message = message;
    }

    public AuthorizationException() {
        this("인증되지 않았습니다.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
