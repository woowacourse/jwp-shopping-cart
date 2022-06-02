package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends IllegalArgumentException {

    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidPasswordException(final String message) {
        super(message);
    }
}
