package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class NotFoundException extends CustomException {

    public NotFoundException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
