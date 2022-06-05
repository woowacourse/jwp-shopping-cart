package woowacourse.auth.exception;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.ClientRuntimeException;

public class PasswordNotMatchException extends ClientRuntimeException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotMatchException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
