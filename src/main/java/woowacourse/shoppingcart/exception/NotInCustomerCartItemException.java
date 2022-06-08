package woowacourse.shoppingcart.exception;

public class NotInCustomerCartItemException extends RuntimeException {

    public NotInCustomerCartItemException() {
        this("존재하지 않는 상품 ID입니다.");
    }

    public NotInCustomerCartItemException(final String msg) {
        super(msg);
    }
}
