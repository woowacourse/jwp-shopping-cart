package woowacourse.auth.exception;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
        super("올바르지 않은 이메일 입력 형식입니다.");
    }
}
