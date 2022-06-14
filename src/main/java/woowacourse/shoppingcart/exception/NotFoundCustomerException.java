package woowacourse.shoppingcart.exception;

public class NotFoundCustomerException extends NotFoundException {

    public NotFoundCustomerException() {
        this("존재하지 않는 유저입니다.");
    }

    public NotFoundCustomerException(final String msg) {
        super(msg);
    }
}
