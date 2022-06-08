package woowacourse.shoppingcart.exception.nobodyexception;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.nobodyexception.NotBodyToReturnException;

public class NotFoundProductException extends NotBodyToReturnException {

    public NotFoundProductException() {
        super(HttpStatus.NOT_FOUND);
    }
}
