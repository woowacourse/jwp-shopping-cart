package woowacourse.exception.unauthorized;

import woowacourse.exception.UnauthorizedException;

public class TokenNotFoundException extends UnauthorizedException {
    private static final String DEFAULT_MESSAGE = "토큰이 존재하지 않습니다";

    public TokenNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
