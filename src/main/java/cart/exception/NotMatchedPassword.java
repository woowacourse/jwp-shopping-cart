package cart.exception;

public class NotMatchedPassword extends RuntimeException {
    public NotMatchedPassword() {
        super("비밀번호가 일치하지 않습니다");
    }
}
