package woowacourse.auth.exception;

import org.springframework.http.HttpStatus;

public final class NotAuthorizationException extends AuthException {

    public NotAuthorizationException() {
        this("로그인이 필요합니다.");
    }

    public NotAuthorizationException(final String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
