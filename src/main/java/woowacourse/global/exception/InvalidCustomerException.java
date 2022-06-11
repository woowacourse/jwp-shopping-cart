package woowacourse.global.exception;

public class InvalidCustomerException extends ShoppingCartException {

    public InvalidCustomerException(final String message) {
        super(message);
    }
}
