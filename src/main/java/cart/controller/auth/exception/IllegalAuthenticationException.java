package cart.controller.auth.exception;

public class IllegalAuthenticationException extends AuthenticationException {

    public IllegalAuthenticationException() {
        super("없거나 잘못된 형식의 인증 정보입니다");
    }
}
