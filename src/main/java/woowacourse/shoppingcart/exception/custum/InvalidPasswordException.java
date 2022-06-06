package woowacourse.shoppingcart.exception.custum;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends IllegalArgumentException {
    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";
    public static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
