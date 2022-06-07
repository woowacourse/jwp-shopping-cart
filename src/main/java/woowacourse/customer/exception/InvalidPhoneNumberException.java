package woowacourse.customer.exception;

import woowacourse.exception.BadRequestException;

public class InvalidPhoneNumberException extends BadRequestException {

    public InvalidPhoneNumberException(final String message) {
        super(message);
    }
}
