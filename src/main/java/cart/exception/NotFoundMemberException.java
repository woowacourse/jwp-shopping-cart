package cart.exception;

public class NotFoundMemberException extends RuntimeException {
    public NotFoundMemberException() {
        super("회원이 존재하지 않습니다");
    }

}
