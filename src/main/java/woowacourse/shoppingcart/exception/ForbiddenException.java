package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CartException {

    public ForbiddenException() {
        super("허가되지 않은 사용자입니다.", HttpStatus.FORBIDDEN);
    }
}
