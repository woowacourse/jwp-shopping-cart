package woowacourse.shoppingcart.exception;

public class NotMyCartItemException extends RuntimeException {
    public NotMyCartItemException() {
        super("Not My Cart Item");
    }
}
