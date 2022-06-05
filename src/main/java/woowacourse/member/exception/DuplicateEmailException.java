package woowacourse.member.exception;

public class DuplicateEmailException extends MemberException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
