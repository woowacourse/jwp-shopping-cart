package cart.exception;

public class UserAccessDeniedException extends RuntimeException {
    public UserAccessDeniedException() {
        super("권한이 없습니다.");
    }
}
