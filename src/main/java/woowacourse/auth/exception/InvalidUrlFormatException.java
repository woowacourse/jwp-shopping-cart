package woowacourse.auth.exception;

public class InvalidUrlFormatException extends RuntimeException {
    public InvalidUrlFormatException() {
        super("올바르지 않은 URL 형식입니다.");
    }
}
