package woowacourse.exception;

public class NotInCustomerCartItemException extends CustomException {

    public NotInCustomerCartItemException() {
        super(Error.NOT_IN_CUSTOMER_CART_ITEM);
    }
}
