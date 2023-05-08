package cart.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class LoginException extends CartException {

	public static final CartException EXCEPTION = new LoginException();

	public LoginException() {
		super(new ErrorCode(401, "LOGIN-401-1", "올바른 패스워드가 아닙니다."));
	}
}
