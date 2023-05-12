package cart.controller.exception;

public class MemberNotFoundException extends IllegalArgumentException {

    public MemberNotFoundException() {
        super("회원 정보가 잘못되었습니다.");
    }
}
