package woowacourse.exception;

public class InvalidProductException extends CustomException {

    public InvalidProductException() {
        super(Error.INVALID_PRODUCT);
    }
}
