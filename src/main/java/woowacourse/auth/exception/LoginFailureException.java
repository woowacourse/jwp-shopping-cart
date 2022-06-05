package woowacourse.auth.exception;

import org.springframework.http.HttpStatus;

public final class LoginFailureException extends AuthException {

    public LoginFailureException() {
        this("일치하는 회원이 없거나 비밀번호가 일치하지 않습니다.");
    }

    public LoginFailureException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
