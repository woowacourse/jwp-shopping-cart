package cart.controller.auth.exception;

public class EmptyAuthenticationException extends AuthenticationException {

    public EmptyAuthenticationException() {
        super("인증 정보가 없습니다");
    }
}
