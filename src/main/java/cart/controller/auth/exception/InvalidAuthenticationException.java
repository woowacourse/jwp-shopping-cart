package cart.controller.auth.exception;

public class InvalidAuthenticationException extends AuthenticationException {

    public InvalidAuthenticationException() {
        super("잘못된 인증 정보입니다");
    }
}
