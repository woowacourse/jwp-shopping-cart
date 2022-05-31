package woowacourse.member.exception;

public class WrongPasswordException extends MemberException {

    public WrongPasswordException(String message) {
        super(message);
    }
}
