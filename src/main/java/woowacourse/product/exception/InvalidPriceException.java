package woowacourse.product.exception;

import woowacourse.exception.BadRequestException;

public class InvalidPriceException extends BadRequestException {

    public InvalidPriceException(final String message) {
        super(message);
    }
}
