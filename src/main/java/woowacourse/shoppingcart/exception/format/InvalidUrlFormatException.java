package woowacourse.shoppingcart.exception.format;

public class InvalidUrlFormatException extends FormatException {
    public InvalidUrlFormatException() {
        super("올바르지 않은 URL 형식입니다.");
    }
}
