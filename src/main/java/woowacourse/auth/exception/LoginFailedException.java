package woowacourse.auth.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("로그인이 실패하였습니다.");
    }
}
