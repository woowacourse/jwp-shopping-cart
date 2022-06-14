package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class ProductException extends CustomException {
    public ProductException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
