package woowacourse.auth.exception.format;

public class InvalidEmailFormatException extends FormatException {
    public InvalidEmailFormatException() {
        super("올바르지 않은 이메일 입력 형식입니다.");
    }
}
