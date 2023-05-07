package cart.controller.exception;

public class IncorrectAuthorizationMethodException extends RuntimeException {
    public IncorrectAuthorizationMethodException() {
        super("[ERROR] 헤더의 인증 방식이 적절하지 않습니다.");
    }
}
