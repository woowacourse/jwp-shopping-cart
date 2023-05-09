package cart.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class NotFoundMemberException extends CartException {

    public static final CartException EXCEPTION = new NotFoundMemberException();

    public NotFoundMemberException() {
        super(new ErrorCode(404, "Member-404-1", "회원을 찾을 수 없습니다."));
    }

}
