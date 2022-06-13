package woowacourse.shoppingcart.exception.custum;

import org.springframework.http.HttpStatus;

public class InvalidLoginException extends RuntimeException {
    private static final String MESSAGE = "로그인 할 수 없습니다.";
    public static final int STATUS_CODE = HttpStatus.UNAUTHORIZED.value();

    public InvalidLoginException() {
        super(MESSAGE);
    }
}
