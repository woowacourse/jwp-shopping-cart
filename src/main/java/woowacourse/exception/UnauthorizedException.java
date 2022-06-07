package woowacourse.exception;

public class UnauthorizedException extends ShoppingCartException {
    private static final String DEFAULT_MESSAGE = "인증 정보가 확인되지 않습니다";

    public UnauthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedException(final String message) {
        super(message);
    }
}
