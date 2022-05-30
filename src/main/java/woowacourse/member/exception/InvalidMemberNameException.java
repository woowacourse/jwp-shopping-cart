package woowacourse.member.exception;

public class InvalidMemberNameException extends IllegalArgumentException {

    public InvalidMemberNameException(String message) {
        super(message);
    }
}
