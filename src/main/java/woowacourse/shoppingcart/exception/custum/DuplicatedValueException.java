package woowacourse.shoppingcart.exception.custum;

import org.springframework.http.HttpStatus;

public class DuplicatedValueException extends RuntimeException {
    private static final String MESSAGE = "중복된 값이 존재합니다.";
    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public DuplicatedValueException() {
        super(MESSAGE);
    }
}
