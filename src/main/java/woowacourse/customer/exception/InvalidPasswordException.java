package woowacourse.customer.exception;

import woowacourse.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {

    public InvalidPasswordException(final String message) {
        super(message);
    }
}
