package woowacourse.shoppingcart.exception.bodyexception;

public class ValidateException extends IllegalRequestException {

    public ValidateException(String errorCode, String message) {
        super(errorCode, message);
    }
}
