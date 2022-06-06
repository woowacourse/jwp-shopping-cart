package woowacourse.shoppingcart.exception.badrequest;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.ShoppingCartException;

public class BadRequestException extends ShoppingCartException {

    public BadRequestException(final String errorCode, final String message) {
        super(errorCode, message, HttpStatus.BAD_REQUEST);
    }
}
