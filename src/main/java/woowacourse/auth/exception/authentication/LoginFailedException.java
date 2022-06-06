package woowacourse.auth.exception.authentication;

public class LoginFailedException extends AuthenticationException {
    public LoginFailedException() {
        super("이메일 혹은 패스워드가 잘못되어 로그인에 실패하였습니다.");
    }
}
