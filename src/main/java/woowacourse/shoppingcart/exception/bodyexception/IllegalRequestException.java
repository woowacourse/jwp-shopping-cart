package woowacourse.shoppingcart.exception.bodyexception;

import org.springframework.http.HttpStatus;
import woowacourse.shoppingcart.exception.bodyexception.BodyToReturnException;

public class IllegalRequestException extends BodyToReturnException {

    public IllegalRequestException(String errorCode, String message) {
        super(errorCode, message, HttpStatus.BAD_REQUEST);
    }
}
