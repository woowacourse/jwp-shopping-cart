package woowacourse.auth.exception.format;

public class InvalidPasswordFormatException extends FormatException {
    public InvalidPasswordFormatException() {
        super("올바르지 않은 패스워드 입력 형식입니다.");
    }
}
