package woowacourse.shoppingcart.exception.custum;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends RuntimeException {
    private static final String MESSAGE = "토큰이 유효하지 않습니다.";
    public static final int STATUS_CODE = HttpStatus.FORBIDDEN.value();

    public AuthorizationException() {
        super(MESSAGE);
    }
}
