package woowacourse.shoppingcart.exception.notfound;

public class NotFoundCartItemException extends NotFoundException {

    public NotFoundCartItemException() {
        super("존재하지 않는 장바구니 아이템입니다😅");
    }
}
