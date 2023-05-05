package cart.controller.auth.exception;

public class AuthenticateFailException extends AuthenticationException {

    public AuthenticateFailException() {
        super("잘못된 인증 정보입니다");
    }
}
