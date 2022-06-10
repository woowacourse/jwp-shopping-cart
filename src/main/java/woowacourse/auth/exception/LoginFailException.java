package woowacourse.auth.exception;

public class LoginFailException extends RuntimeException {

    public LoginFailException() {
        this("id 또는 비밀번호가 틀렸습니다.");
    }

    public LoginFailException(final String message) {
        super(message);
    }
}
