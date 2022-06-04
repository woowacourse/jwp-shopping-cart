package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("인증되지 않은 회원입니다.");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
