package woowacourse.shoppingcart.exception.nobodyexception;

import org.springframework.http.HttpStatus;

public class NotFoundProductException extends NotBodyToReturnException {

    public NotFoundProductException() {
        super(HttpStatus.NOT_FOUND);
    }
}
