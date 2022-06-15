package woowacourse.shoppingcart.exception.notfound;

public class NotFoundOrderException extends NotFoundException {

    public NotFoundOrderException() {
        super("존재하지 않는 주문입니다😅");
    }
}
