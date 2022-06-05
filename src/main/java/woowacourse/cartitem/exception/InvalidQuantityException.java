package woowacourse.cartitem.exception;

import woowacourse.exception.BadRequestException;

public class InvalidQuantityException extends BadRequestException {

    public InvalidQuantityException(final String message) {
        super(message);
    }
}
