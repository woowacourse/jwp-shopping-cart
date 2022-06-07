package woowacourse.shoppingcart.exception.cart;

public class NotInMemberCartException extends CartException {

    public NotInMemberCartException(String message) {
        super(message);
    }
}
