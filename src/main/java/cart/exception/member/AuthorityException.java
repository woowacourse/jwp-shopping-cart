package cart.exception.member;

public class AuthorityException  extends MemberException {

    private static final String AUTHORITY_ERROR_MESSAGE = "인가되지 않은 회원입니다.";

    public AuthorityException() {
        super(AUTHORITY_ERROR_MESSAGE);
    }
}
