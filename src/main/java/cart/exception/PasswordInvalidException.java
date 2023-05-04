package cart.exception;

public class PasswordInvalidException extends RuntimeException {

    public PasswordInvalidException() {
        super("패스워드가 일치하지 않습니다.");
    }
}
