package woowacourse.exception.unauthorization;

public class UnauthorizedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "인증 정보가 확인되지 않습니다";

    public UnauthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
