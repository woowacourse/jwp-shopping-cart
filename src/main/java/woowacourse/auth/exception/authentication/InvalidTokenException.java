package woowacourse.auth.exception.authentication;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}
