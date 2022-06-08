package woowacourse.exception;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException() {
        super(Error.INVALID_TOKEN);
    }
}
