package woowacourse.shoppingcart.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        this("로그인하지 않아 접근할 수 없습니다.");
    }

    public AuthorizationException(final String msg) {
        super(msg);
    }
}
