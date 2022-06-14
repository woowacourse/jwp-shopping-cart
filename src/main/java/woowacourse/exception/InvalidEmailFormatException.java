package woowacourse.exception;

public class InvalidEmailFormatException extends CustomException {

    public InvalidEmailFormatException() {
        super(Error.INVALID_EMAIL_FORMAT);
    }
}
