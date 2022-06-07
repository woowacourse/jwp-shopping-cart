package woowacourse.customer.exception;

import woowacourse.exception.BadRequestException;

public class InvalidUsernameException extends BadRequestException {

    public InvalidUsernameException(final String message) {
        super(message);
    }
}
