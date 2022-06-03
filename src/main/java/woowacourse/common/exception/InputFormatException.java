package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class InputFormatException extends CustomException {

    public InputFormatException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
