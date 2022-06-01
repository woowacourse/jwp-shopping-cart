package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidCustomerException extends RuntimeException {

    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidCustomerException(final String message) {
        super(message);
    }
}
