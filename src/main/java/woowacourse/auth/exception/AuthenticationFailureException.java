package woowacourse.auth.exception;

public class AuthenticationFailureException extends RuntimeException {

    private static final String ERROR_MESSAGE = "로그인에 실패했습니다.";

    public AuthenticationFailureException() {
        super(ERROR_MESSAGE);
    }
}
