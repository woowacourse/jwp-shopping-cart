package woowacourse.exception;

import woowacourse.exception.dto.ErrorResponse;

public class InputFormatException extends CustomException {

    public InputFormatException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
