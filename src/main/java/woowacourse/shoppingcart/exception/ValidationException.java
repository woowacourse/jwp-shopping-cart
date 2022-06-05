package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public abstract class ValidationException extends CartException {

    private final String field;

    public ValidationException(final String message, final String field) {
        super(message, HttpStatus.BAD_REQUEST);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
