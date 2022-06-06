package woowacourse.shoppingcart.exception;

public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException() {
        this("중복되는 고객이 존재합니다.");
    }

    public DuplicateCustomerException(final String msg) {
        super(msg);
    }
}
