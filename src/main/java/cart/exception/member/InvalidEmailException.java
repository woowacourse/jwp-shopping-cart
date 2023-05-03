package cart.exception.member;

public class InvalidEmailException extends MemberException{

    private static final String INVALID_EMAIL_MESSAGE = "잘못된 형식의 이메일 입니다.";

    public InvalidEmailException() {
        super(INVALID_EMAIL_MESSAGE);
    }
}
