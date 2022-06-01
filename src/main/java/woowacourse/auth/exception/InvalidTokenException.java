package woowacourse.auth.exception;

public class InvalidTokenException extends RuntimeException {

    private static final String EXPIRED_TOKEN = "[ERROR] 만료된 토큰입니다.";

    public InvalidTokenException() {
        super(EXPIRED_TOKEN);
    }
}
