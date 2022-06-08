package woowacourse.exception;

public class InvalidPasswordFormatException extends CustomException {

    public InvalidPasswordFormatException() {
        super(Error.INVALID_PASSWORD_FORMAT);
    }
}
