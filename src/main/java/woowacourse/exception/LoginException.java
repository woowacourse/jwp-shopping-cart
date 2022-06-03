package woowacourse.exception;

import woowacourse.exception.dto.ErrorResponse;

public class LoginException extends CustomException {

    public LoginException(String message, ErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
