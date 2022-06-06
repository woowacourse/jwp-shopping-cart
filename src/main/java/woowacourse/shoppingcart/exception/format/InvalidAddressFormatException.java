package woowacourse.shoppingcart.exception.format;

public class InvalidAddressFormatException extends FormatException {
    public InvalidAddressFormatException() {
        super("올바르지 않은 주소 형식입니다.");
    }
}
