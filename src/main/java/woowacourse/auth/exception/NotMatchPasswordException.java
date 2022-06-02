package woowacourse.auth.exception;

import org.springframework.http.HttpStatus;

public final class NotMatchPasswordException extends AuthException {

    public NotMatchPasswordException() {
        this("비밀번호가 일치하지 않습니다.");
    }

    public NotMatchPasswordException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
