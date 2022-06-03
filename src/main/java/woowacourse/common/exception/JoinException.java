package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class JoinException extends CustomException {

    public JoinException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
