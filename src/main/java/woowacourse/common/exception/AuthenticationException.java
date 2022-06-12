package woowacourse.common.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public static AuthenticationException ofLoginFailure() {
        return new AuthenticationException("로그인에 실패하였습니다.");
    }

    public static AuthenticationException ofInvalidToken() {
        return new AuthenticationException("다시 로그인해주세요.");
    }

    public static AuthenticationException ofUnauthenticated() {
        return new AuthenticationException("로그인이 필요합니다.");
    }
}
