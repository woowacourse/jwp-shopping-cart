package woowacourse.member.exception;

public class DuplicateEmailException extends MemberException {

    public DuplicateEmailException() {
        super("중복되는 이메일이 존재합니다.");
    }
}
