package cart.security.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class HeaderPrefixNotFoundException extends CartException {

    public static final CartException EXCEPTION = new HeaderPrefixNotFoundException();

    public HeaderPrefixNotFoundException() {
        super(new ErrorCode(404, "HTTP-404-1", "Authorization 헤더를 찾을 수 없습니다."));
    }

}
