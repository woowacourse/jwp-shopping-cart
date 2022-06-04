package woowacourse.auth.exception;

public class AuthException extends RuntimeException {

    public AuthException() {
        this("유효하지 않은 인증입니다.");
    }

    public AuthException(final String msg) {
        super(msg);
    }
}
