package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        this("비밀번호가 틀렸습니다.");
    }

    public InvalidPasswordException(final String msg) {
        super(msg);
    }
}
