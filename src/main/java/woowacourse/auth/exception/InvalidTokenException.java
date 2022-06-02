package woowacourse.auth.exception;

import org.springframework.http.HttpStatus;

public final class InvalidTokenException extends AuthException {

    public InvalidTokenException() {
        this("유효하지 않은 토큰입니다.");
    }

    public InvalidTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
