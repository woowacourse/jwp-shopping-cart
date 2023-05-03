package cart.security.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class PasswordNotMatchException extends CartException {

    public static final CartException EXCEPTION = new PasswordNotMatchException();

    public PasswordNotMatchException() {
        super(new ErrorCode(401, "AUTH-404-1", "비밀번호가 일치하지 않습니다."));
    }

}
