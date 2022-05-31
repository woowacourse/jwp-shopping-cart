package woowacourse.auth.application;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("인증에 실패하였습니다.");
    }
}
