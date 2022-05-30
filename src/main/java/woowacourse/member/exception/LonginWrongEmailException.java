package woowacourse.member.exception;

public class LonginWrongEmailException extends IllegalArgumentException {

    public LonginWrongEmailException(String message) {
        super(message);
    }
}
