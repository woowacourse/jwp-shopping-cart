package cart.controller.auth;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("인증되지 않았습니다.");
    }

}
