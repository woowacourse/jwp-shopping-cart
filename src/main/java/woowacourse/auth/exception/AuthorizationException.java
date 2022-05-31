package woowacourse.auth.exception;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        this("인증에 실패하였습니다.");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
