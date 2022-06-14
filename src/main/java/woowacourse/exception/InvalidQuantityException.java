package woowacourse.exception;

public class InvalidQuantityException extends CustomException {

    public InvalidQuantityException() {
        super(Error.INVALID_QUANTITY);
    }
}
