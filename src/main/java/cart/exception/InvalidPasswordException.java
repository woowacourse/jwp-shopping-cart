package cart.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("올바르지 않은 비밀번호 입니다.");
    }
}
