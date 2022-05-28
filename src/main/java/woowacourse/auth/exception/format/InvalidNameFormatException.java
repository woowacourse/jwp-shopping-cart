package woowacourse.auth.exception.format;

public class InvalidNameFormatException extends FormatException {
    public InvalidNameFormatException() {
        super("올바르지 않은 이름 형식입니다.");
    }
}
