package woowacourse.auth.exception.authorization;

public class ExpiredTokenException extends AuthorizationException {
    public ExpiredTokenException() {
        super("만료된 토큰입니다.");
    }
}
