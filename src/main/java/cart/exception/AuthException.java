package cart.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class AuthException extends CartException {

	public static final CartException EXCEPTION = new AuthException();

	public AuthException() {
		super(new ErrorCode(401, "AUTH-401-1", "Header 값이 비어있습니다."));
	}
}
