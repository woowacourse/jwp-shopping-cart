package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class DuplicateCustomerException extends CartException {

    public DuplicateCustomerException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
