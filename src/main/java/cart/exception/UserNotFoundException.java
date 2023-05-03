package cart.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("회원을 찾을 수가 없습니다.");
    }
}
