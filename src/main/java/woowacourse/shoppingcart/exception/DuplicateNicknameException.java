package woowacourse.shoppingcart.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

public class DuplicateNicknameException extends DuplicateKeyException {

    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public DuplicateNicknameException(final String message) {
        super(message);
    }
}
