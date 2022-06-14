package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class OrderException extends CustomException {

    public OrderException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
