package cart.service;

public class PasswordMismatchException extends IllegalArgumentException {

    private static final String MESSAGE = "password가 일치하지 않습니다.";

    public PasswordMismatchException() {
        super(MESSAGE);
    }
}
