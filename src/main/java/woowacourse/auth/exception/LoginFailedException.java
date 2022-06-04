package woowacourse.auth.exception;

public class LoginFailedException extends IllegalArgumentException {

    public LoginFailedException() {
        super("이메일에 해당하는 회원이 존재하지 않거나 비밀번호가 일치하지 않습니다.");
    }
}
