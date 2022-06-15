package woowacourse.shoppingcart.exception.notfound;

public class NotFoundProductException extends NotFoundException {

    public NotFoundProductException() {
        super("존재하지 않는 상품입니다😅");
    }
}
