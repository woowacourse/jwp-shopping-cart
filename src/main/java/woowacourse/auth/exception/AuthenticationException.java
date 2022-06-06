package woowacourse.auth.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        this("인증에 실패하였습니다.");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
