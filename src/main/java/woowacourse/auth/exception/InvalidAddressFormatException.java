package woowacourse.auth.exception;

public class InvalidAddressFormatException extends RuntimeException {
    public InvalidAddressFormatException() {
        super("올바르지 않은 주소 형식입니다.");
    }
}
