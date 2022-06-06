package woowacourse.shoppingcart.exception;

public class IllegalProductException extends IllegalRequestException {

    public IllegalProductException(String message) {
        super("", message);
    }
}
