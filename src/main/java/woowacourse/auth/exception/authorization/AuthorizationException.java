package woowacourse.auth.exception.authorization;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("접근 권한이 없습니다.");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
