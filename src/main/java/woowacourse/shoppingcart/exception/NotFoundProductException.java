package woowacourse.shoppingcart.exception;

import org.springframework.http.HttpStatus;

public class NotFoundProductException extends NotBodyToReturnException {

    public NotFoundProductException() {
        super(HttpStatus.NOT_FOUND);
    }
}
