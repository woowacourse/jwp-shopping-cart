package woowacourse.auth.exception;

public class NoSuchEmailException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 이메일입니다.";

    public NoSuchEmailException() {
        super(MESSAGE);
    }
}
