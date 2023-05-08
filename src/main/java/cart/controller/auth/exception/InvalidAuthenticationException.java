package cart.controller.auth.exception;

public class InvalidAuthenticationException extends AuthenticationException {

    public InvalidAuthenticationException() {
        super("유효하지 않은 인증 정보입니다");
    }
}
