package woowacourse.shoppingcart.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

public class DuplicateUsernameException extends DuplicateKeyException {

    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public DuplicateUsernameException(final String message) {
        super(message);
    }
}
