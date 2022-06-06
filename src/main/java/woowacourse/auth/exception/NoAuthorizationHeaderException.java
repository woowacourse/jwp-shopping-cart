package woowacourse.auth.exception;

public class NoAuthorizationHeaderException extends RuntimeException {
    public NoAuthorizationHeaderException() {
        this("authorization header가 없습니다.");
    }

    public NoAuthorizationHeaderException(final String msg) {
        super(msg);
    }
}
