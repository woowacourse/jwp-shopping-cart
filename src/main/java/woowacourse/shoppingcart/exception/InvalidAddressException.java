package woowacourse.shoppingcart.exception;

public class InvalidAddressException extends InvalidPropertyException {

    public InvalidAddressException() {
        this("");
    }

    public InvalidAddressException(String message) {
        super(message);
    }
}
