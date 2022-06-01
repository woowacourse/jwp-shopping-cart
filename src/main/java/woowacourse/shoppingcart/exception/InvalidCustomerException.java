package woowacourse.shoppingcart.exception;

public class InvalidCustomerException extends RuntimeException {

    public InvalidCustomerException(final String msg) {
        super(msg);
    }
}
