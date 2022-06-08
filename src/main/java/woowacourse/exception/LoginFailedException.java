package woowacourse.exception;

public class LoginFailedException extends CustomException {

    public LoginFailedException() {
        super(Error.LOGIN_FAILED);
    }
}
