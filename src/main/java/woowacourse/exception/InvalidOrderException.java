package woowacourse.exception;

public class InvalidOrderException extends CustomException {

    public InvalidOrderException() {
        super(Error.INVALID_ORDER);
    }
}
