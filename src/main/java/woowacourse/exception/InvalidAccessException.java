package woowacourse.exception;

public class InvalidAccessException extends CustomException {

    public InvalidAccessException() {
        super(Error.NEED_AUTHORIZATION);
    }
}
