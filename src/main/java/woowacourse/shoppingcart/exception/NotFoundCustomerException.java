package woowacourse.shoppingcart.exception;

public class NotFoundCustomerException extends NotFoundException {

    public NotFoundCustomerException() {
        super("999", "회원이 존재하지 않습니다.");
    }
}
