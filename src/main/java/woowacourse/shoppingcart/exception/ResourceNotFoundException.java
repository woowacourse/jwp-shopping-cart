package woowacourse.shoppingcart.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends NoSuchElementException {

    public static final int STATUS_CODE = HttpStatus.NOT_FOUND.value();

    public ResourceNotFoundException(final String s) {
        super(s);
    }
}
