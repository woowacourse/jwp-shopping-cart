package woowacourse.shoppingcart.exception.auth;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        this("인증되지 않은 사용자입니다😤");
    }

    public AuthorizationException(final String message) {
        super(message);
    }
}
