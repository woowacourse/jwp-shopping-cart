package woowacourse.auth.exception;

public class InvalidZoneCodeFormatException extends RuntimeException {
    public InvalidZoneCodeFormatException() {
        super("올바르지 않은 우편번호 형식입니다.");
    }
}
