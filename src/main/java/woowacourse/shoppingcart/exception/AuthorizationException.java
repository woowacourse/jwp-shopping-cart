package woowacourse.shoppingcart.exception;

public class AuthorizationException extends RuntimeException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public AuthorizationException() {
        super(MESSAGE);
    }
}
