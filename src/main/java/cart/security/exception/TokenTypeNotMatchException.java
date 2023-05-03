package cart.security.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class TokenTypeNotMatchException extends CartException {

    public static final CartException EXCEPTION = new TokenTypeNotMatchException();

    public TokenTypeNotMatchException() {
        super(new ErrorCode(400, "HTTP-400-2", "토큰의 타입이 Basic 형식이 아닙니다."));
    }

}
