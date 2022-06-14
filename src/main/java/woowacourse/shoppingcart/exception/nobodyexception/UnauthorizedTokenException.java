package woowacourse.shoppingcart.exception.nobodyexception;

import org.springframework.http.HttpStatus;

public class UnauthorizedTokenException extends NotBodyToReturnException {

    public UnauthorizedTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
