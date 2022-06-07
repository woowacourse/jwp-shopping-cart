package woowacourse.member.exception;

public class DuplicateEmailException extends MemberException {

    public DuplicateEmailException() {
        super("이메일은 중복될 수 없습니다.");
    }
}
