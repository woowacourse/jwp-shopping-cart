package woowacourse.auth.exception;

public class PasswordNotMatchException extends RuntimeException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotMatchException() {
        super(MESSAGE);
    }
}
