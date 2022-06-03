package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class AuthException extends CustomException {

    public AuthException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
