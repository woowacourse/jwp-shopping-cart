package woowacourse.exception;

public class LoginFailureException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "로그인에 실패했습니다";

    public LoginFailureException() {
        super(DEFAULT_MESSAGE);
    }
}
