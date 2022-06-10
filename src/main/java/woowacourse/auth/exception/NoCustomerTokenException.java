package woowacourse.auth.exception;

public class NoCustomerTokenException extends RuntimeException {

    public NoCustomerTokenException() {
        this("존재하지 않는 유저의 토큰입니다.");
    }

    public NoCustomerTokenException(final String message) {
        super(message);
    }
}
