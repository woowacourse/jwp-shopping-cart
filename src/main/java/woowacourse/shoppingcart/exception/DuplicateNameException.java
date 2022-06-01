package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public final class DuplicateNameException extends CartException {
    public DuplicateNameException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
