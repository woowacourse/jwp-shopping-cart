package woowacourse.shoppingcart.exception.custum;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends IllegalArgumentException {
    private static final String MESSAGE_FORMAT = "올바르지 않은 포맷의 %s 입니다.";
    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidInputException(final String message) {
        super(String.format(MESSAGE_FORMAT, message));
    }
}
