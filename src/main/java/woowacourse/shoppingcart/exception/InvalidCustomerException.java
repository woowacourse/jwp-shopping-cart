package woowacourse.shoppingcart.exception;

public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException() {
        this("유효하지 않은 고객입니다");
    }

    public InvalidCustomerException(final String msg) {
        super(msg);
    }
}
