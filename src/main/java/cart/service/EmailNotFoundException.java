package cart.service;

public class EmailNotFoundException extends IllegalArgumentException {

    private static final String MESSAGE = "존재하지 않는 email입니다.";

    public EmailNotFoundException() {
        super(MESSAGE);
    }
}
