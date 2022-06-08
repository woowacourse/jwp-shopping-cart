package woowacourse.shoppingcart.exception;

public class InvalidNicknameFormatException extends RuntimeException {
    public InvalidNicknameFormatException() {
    }

    public InvalidNicknameFormatException(String message) {
        super(message);
    }
}
