package woowacourse.member.exception;

public class WrongPasswordException extends IllegalArgumentException {

    public WrongPasswordException(String message) {
        super(message);
    }
}
