package woowacourse.member.exception;

public class InvalidMemberEmailException extends IllegalArgumentException {

    public InvalidMemberEmailException(String message) {
        super(message);
    }
}
