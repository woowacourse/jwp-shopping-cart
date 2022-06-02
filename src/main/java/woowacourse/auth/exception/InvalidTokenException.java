package woowacourse.auth.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        this("인증이 유효하지 않습니다.");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
