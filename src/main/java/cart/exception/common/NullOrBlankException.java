package cart.exception.common;

import cart.exception.product.ProductException;

public class NullOrBlankException extends CommonException {

    private static final String NULL_OR_BLANCK_ERROR_MESSAGE = "null이나 빈 값이 될 수 없습니다.";

    public NullOrBlankException() {
        super(NULL_OR_BLANCK_ERROR_MESSAGE);
    }
}
