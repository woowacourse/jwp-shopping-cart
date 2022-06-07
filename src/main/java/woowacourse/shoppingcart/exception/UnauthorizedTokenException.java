package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedTokenException extends NotBodyToReturnException {

    public UnauthorizedTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
