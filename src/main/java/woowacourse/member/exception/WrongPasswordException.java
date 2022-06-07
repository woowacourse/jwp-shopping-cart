package woowacourse.member.exception;

public class WrongPasswordException extends MemberException {

    public WrongPasswordException() {
        super("잘못된 비밀번호입니다.");
    }
}
