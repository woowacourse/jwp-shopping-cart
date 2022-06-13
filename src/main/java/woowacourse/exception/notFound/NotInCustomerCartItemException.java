package woowacourse.exception.notFound;

public class NotInCustomerCartItemException extends NotFoundException {
    public NotInCustomerCartItemException() {
        this("장바구니 아이템이 없습니다.");
    }

    public NotInCustomerCartItemException(final String msg) {
        super(msg);
    }
}
