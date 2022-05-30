package woowacourse.auth.exception;

public class LoginFailException extends RuntimeException {

    public LoginFailException() {
        this("로그인에 실패했습니다.");
    }

    public LoginFailException(final String msg) {
        super(msg);
    }
}
