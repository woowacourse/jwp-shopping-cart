package woowacourse.shoppingcart.exception.notfound;

public class NotFoundCustomerException extends NotFoundException {

    public NotFoundCustomerException() {
        super("존재하지 않는 사용자입니다😅");
    }
}
