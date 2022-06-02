package woowacourse.auth.exception.authentication;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("사용자 인증에 실패하였습니다.");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
