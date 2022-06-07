package woowacourse.exception.unauthorized;

import woowacourse.exception.UnauthorizedException;

public class PasswordIncorrectException extends UnauthorizedException {
    private static final String DEFAULT_MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordIncorrectException() {
        super(DEFAULT_MESSAGE);
    }
}
