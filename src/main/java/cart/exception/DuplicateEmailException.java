package cart.exception;

public class DuplicateEmailException extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 이메일 입니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }

    public DuplicateEmailException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
