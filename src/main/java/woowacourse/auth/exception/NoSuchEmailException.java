package woowacourse.auth.exception;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.ClientRuntimeException;

public class NoSuchEmailException extends ClientRuntimeException {

    private static final String MESSAGE = "존재하지 않는 이메일입니다.";

    public NoSuchEmailException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
