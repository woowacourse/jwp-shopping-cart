package woowacourse.shoppingcart.exception;

public class InvalidCustomerException extends DomainException {

    public InvalidCustomerException() {
        this("존재하지 않는 유저입니다.");
    }

    public InvalidCustomerException(final String msg) {
        super(msg);
    }
}
