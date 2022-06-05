package woowacourse.common.exception;

import woowacourse.common.exception.dto.ErrorResponse;

public class LoginException extends CustomException {

    public LoginException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
