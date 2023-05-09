package cart.exception;

public class IncorrectEmailOrPasswordException extends IllegalArgumentException {

    private final static String MESSAGE = "잘못된 이메일 혹은 비밀번호 입니다.";

    public IncorrectEmailOrPasswordException() {
        super(MESSAGE);
    }
}
