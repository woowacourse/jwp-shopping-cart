package woowacourse.auth.exception;

public class InvalidContactFormatException extends RuntimeException {
    public InvalidContactFormatException() {
        super("올바르지 않은 전화번호 형식입니다.");
    }
}
