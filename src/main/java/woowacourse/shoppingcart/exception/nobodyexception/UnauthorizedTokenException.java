package woowacourse.shoppingcart.exception.nobodyexception;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.nobodyexception.NotBodyToReturnException;

public class UnauthorizedTokenException extends NotBodyToReturnException {

    public UnauthorizedTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
