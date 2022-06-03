package woowacourse.exception;

import woowacourse.exception.dto.ErrorResponse;

public class JoinException extends CustomException {

    public JoinException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
