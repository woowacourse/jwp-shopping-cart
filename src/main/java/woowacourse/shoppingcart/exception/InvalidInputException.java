package woowacourse.shoppingcart.exception;

public class InvalidInputException extends IllegalArgumentException {

    public InvalidInputException(final String msg) {
        super(msg);
    }
}
