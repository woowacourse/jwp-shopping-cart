package woowacourse.shoppingcart.exception;

public class NotFoundProductException extends NotFoundException {

    public NotFoundProductException() {
        super("", "상품이 존재하지 않습니다.");

    }
}
