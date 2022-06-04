package woowacourse.auth.exception;

public class InvalidTokenException extends RuntimeException {

    private static final String ERROR_MESSAGE = "토큰이 유효하지 않습니다.";

    public InvalidTokenException() {
        super(ERROR_MESSAGE);
    }
}
