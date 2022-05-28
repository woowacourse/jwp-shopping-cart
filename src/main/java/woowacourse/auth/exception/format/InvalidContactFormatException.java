package woowacourse.auth.exception.format;

public class InvalidContactFormatException extends FormatException {
    public InvalidContactFormatException() {
        super("올바르지 않은 전화번호 형식입니다.");
    }
}
