package woowacourse.auth.exception;

import woowacourse.exception.UnauthorizedException;

public class InvalidLoginException extends UnauthorizedException {

    public InvalidLoginException(String message) {
        super(message);
    }
}
