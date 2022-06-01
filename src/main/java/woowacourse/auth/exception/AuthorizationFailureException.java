package woowacourse.auth.exception;

public class AuthorizationFailureException extends RuntimeException {

    private static final String MESSAGE = "사용자 인증에 실패했습니다.";

    public AuthorizationFailureException() {
        super(MESSAGE);
    }
}
