package woowacourse.shoppingcart.exception.order;

public class NotInMemberOrderException extends OrderException {

    public NotInMemberOrderException(String message) {
        super(message);
    }
}
