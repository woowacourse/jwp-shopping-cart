package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidLoginException extends RuntimeException{

    public static final int STATUS_CODE = HttpStatus.UNAUTHORIZED.value();

    public InvalidLoginException(final String message) {
        super(message);
    }
}
