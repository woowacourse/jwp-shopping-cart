package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class DuplicateNameException extends CartException {
    public DuplicateNameException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
