package woowacourse.member.exception;

public class LonginWrongPasswordException extends IllegalArgumentException {

    public LonginWrongPasswordException(String message) {
        super(message);
    }
}
