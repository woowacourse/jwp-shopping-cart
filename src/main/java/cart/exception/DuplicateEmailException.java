package cart.exception;

public class DuplicateEmailException extends IllegalArgumentException {

    private final static String MESSAGE = "중복된 이메일입니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }
}
