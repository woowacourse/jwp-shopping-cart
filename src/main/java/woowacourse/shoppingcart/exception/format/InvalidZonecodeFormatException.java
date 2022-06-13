package woowacourse.shoppingcart.exception.format;

public class InvalidZonecodeFormatException extends FormatException {
    public InvalidZonecodeFormatException() {
        super("올바르지 않은 우편번호 형식입니다.");
    }
}
