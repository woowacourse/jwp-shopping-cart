package woowacourse.global.exception;

public class InvalidCustomerException extends ShoppingCartException {

    public InvalidCustomerException(String message) {
        super(message);
    }
}
