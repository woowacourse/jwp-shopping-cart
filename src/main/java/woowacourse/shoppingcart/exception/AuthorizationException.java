package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends RuntimeException{

    public static final int STATUS_CODE = HttpStatus.FORBIDDEN.value();

    public AuthorizationException(final String message) {
        super(message);
    }
}
