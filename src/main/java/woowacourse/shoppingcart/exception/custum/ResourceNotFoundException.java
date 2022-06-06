package woowacourse.shoppingcart.exception.custum;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends NoSuchElementException {
    private static final String MESSAGE = "존재하지 않는 데이터입니다.";
    public static final int STATUS_CODE = HttpStatus.NOT_FOUND.value();

    public ResourceNotFoundException() {
        super(MESSAGE);
    }
}
