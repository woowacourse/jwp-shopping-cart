package woowacourse.product.exception;

import woowacourse.exception.BadRequestException;

public class InvalidStockException extends BadRequestException {

    public InvalidStockException(final String message) {
        super(message);
    }
}
