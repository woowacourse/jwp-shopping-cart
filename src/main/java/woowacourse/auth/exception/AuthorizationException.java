package woowacourse.auth.exception;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        this("인증이 유효하지 않습니다.");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
