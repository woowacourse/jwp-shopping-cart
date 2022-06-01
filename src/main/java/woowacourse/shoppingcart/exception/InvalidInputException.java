package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends IllegalArgumentException {

    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidInputException(final String message) {
        super(message);
    }
}
