package woowacourse.member.exception;

public class InvalidMemberEmailException extends MemberException {

    public InvalidMemberEmailException() {
        super("올바르지 못한 이메일 형식입니다.");
    }
}
