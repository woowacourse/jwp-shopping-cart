package cart.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("해당하는 사용자가 없습니다.");
    }
}
