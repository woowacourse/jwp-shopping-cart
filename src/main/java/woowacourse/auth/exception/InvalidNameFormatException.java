package woowacourse.auth.exception;

public class InvalidNameFormatException extends RuntimeException {
    public InvalidNameFormatException() {
        super("올바르지 않은 이름 형식입니다.");
    }
}
